package com.lrc.ocr.http;

import com.lrc.ocr.domain.user.model.entity.UserEntity;
import com.lrc.ocr.domain.user.model.vo.LoginUserVO;
import com.lrc.ocr.domain.user.service.IUserService;
import com.lrc.ocr.model.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/auth")
@RestController
@Api(tags = "验证相关接口")
public class AuthController {

    @Resource
    private IUserService userService;

    @PostMapping("/getAuth")
    public Result<LoginUserVO> getAuth(String code){
        LoginUserVO loginUserVO = userService.login(code);
        return Result.success(loginUserVO);
    }

    @GetMapping("/getCurrentUser")
    public Result<UserEntity> getCurrentUser(){
        UserEntity userEntity = userService.getCurrentUser();
        return Result.success(userEntity);
    }
}
