package com.lrc.ocr.domain.user.service.impl;

import com.lrc.ocr.domain.user.model.entity.LoginUserEntity;
import com.lrc.ocr.domain.user.model.entity.UserEntity;
import com.lrc.ocr.domain.user.repository.IUserRepository;
import com.lrc.ocr.enums.BaseError;
import com.lrc.ocr.exception.ServiceException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String openid) throws UsernameNotFoundException {
        // 判空校验
        if (StringUtils.isBlank(openid)){
            throw new ServiceException(BaseError.LOGIN_ERROR);
        }
        // 查询数据库有没有当前用户
        UserEntity userEntity = userRepository.queryUserByOpenid(openid);
        // 如果没有就插入
        if (ObjectUtils.isEmpty(userEntity)){
            UserEntity insertUser = new UserEntity()
                    .setOpenid(openid)
                    .setLines(100);
            userRepository.insert(insertUser);
            userEntity = userRepository.queryUserByOpenid(openid);
        }
        // 判空校验
        if (ObjectUtils.isEmpty(userEntity)){
            throw new ServiceException(BaseError.LOGIN_ERROR);
        }
        // 返回封装的实体类
        return new LoginUserEntity(userEntity);
    }
}
