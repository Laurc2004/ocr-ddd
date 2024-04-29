package com.lrc.ocr.domain.user.service;

import com.lrc.ocr.domain.user.model.entity.UserEntity;
import com.lrc.ocr.domain.user.model.vo.LoginUserVO;

public interface IUserService {
    LoginUserVO login(String code);

    UserEntity getCurrentUser();
//    LoginUserVO login(String username, String password);
//
//    void logout();
}
