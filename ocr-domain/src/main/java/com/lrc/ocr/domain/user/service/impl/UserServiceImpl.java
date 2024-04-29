package com.lrc.ocr.domain.user.service.impl;

import com.lrc.ocr.constants.RedisConstants;
import com.lrc.ocr.domain.user.model.entity.LoginUserEntity;
import com.lrc.ocr.domain.user.model.entity.UserEntity;
import com.lrc.ocr.domain.user.model.vo.LoginUserVO;
import com.lrc.ocr.domain.user.repository.IUserRepository;
import com.lrc.ocr.domain.user.service.IUserService;
import com.lrc.ocr.enums.BaseError;
import com.lrc.ocr.exception.ServiceException;
import com.lrc.ocr.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.lrc.ocr.constants.HttpConstants.SALT;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {


    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private IUserRepository userRepository;

    /**
     * 验证码登录校验
     * @param code
     * @return
     */
    @Override
    public LoginUserVO login(String code) {
        // 除去空值
        code = code.trim();
        String openid = redisTemplate.opsForValue().get(RedisConstants.CODE_KEY + code);

        // 校验验证码
        if (StringUtils.isBlank(openid)){
            throw new ServiceException(BaseError.CODE_ERROR);
        }

        // 调用 UserDetailsService 方法
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(openid,SALT);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if (ObjectUtils.isEmpty(authenticate)) {
            throw new ServiceException(BaseError.LOGIN_ERROR);
        }

        // 生成token
        // 一路获取下去
        LoginUserEntity loginUserEntity = (LoginUserEntity)authenticate.getPrincipal();

        UserEntity userEntity = loginUserEntity.getUserEntity();
        Long id = userEntity.getId();

        String token = JwtUtil.createJWT(String.valueOf(id));
        destroyCode(code,openid);
        return new LoginUserVO(token);
    }

    @Override
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            throw new ServiceException(BaseError.LOGIN_USER_NOT_LOGIN_ERROR);
        }

        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
        Long id = Long.parseLong((String) auth.getPrincipal()); // 获取id

        if (ObjectUtils.isEmpty(id)) {
            throw new ServiceException(BaseError.LOGIN_USER_NOT_LOGIN_ERROR);
        }

        UserEntity userEntity = userRepository.getById(id);
        return userEntity;
    }

    private void destroyCode(String code, String openid){
        redisTemplate.delete(RedisConstants.CODE_KEY + code);
        redisTemplate.delete(RedisConstants.OPENID_KEY + openid);
    }



//    /**
//     * 登录
//     * @param username 用户名
//     * @param password 密码
//     * @return 返回一个包含token的LoginUserVO对象
//     */
//    @Override
//    public LoginUserVO login(String username, String password) {
//        // 创建一个UsernamePasswordAuthenticationToken对象，用于封装用户名和密码
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//        // 使用AuthenticationManager进行身份验证，返回一个包含认证信息的Authentication对象
//        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//
//        // 如果Authentication对象为空，抛出一个ServiceException异常，表示用户登录失败
//        if (ObjectUtils.isEmpty(authenticate)) {
//            throw new ServiceException(USER_LOGIN_ERROR.getCode(), USER_LOGIN_ERROR.getMsg());
//        }
//
//        // 从Authentication对象中获取用户信息
//        LoginUserEntity loginUser = (LoginUserEntity) authenticate.getPrincipal();
//        UserEntity userEntity = loginUser.getUserEntity();
//        // 使用JwtUtil生成一个token
//        String token = JwtUtil.createJWT(userEntity.getId().toString(),2 * JwtUtil.JWT_TTL);
//        // 将用户信息存入Redis，键为"LOGIN_USER_KEY + 用户ID"，值为用户信息，有效期为2小时
//        redisTemplate.opsForValue().set(LOGIN_USER_KEY + userEntity.getId(), loginUser,2, TimeUnit.HOURS);
//
//        // 返回一个包含token的LoginUserVO对象
//        return new LoginUserVO(token);
//    }
//
//


}
