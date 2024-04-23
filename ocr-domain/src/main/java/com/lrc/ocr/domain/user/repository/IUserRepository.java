package com.lrc.ocr.domain.user.repository;

import com.lrc.ocr.domain.user.model.entity.UserEntity;

public interface IUserRepository {
    UserEntity queryUserByOpenid(String openid);

    void insert(UserEntity insertUser);
//    UserEntity getByUsername(String username);
}
