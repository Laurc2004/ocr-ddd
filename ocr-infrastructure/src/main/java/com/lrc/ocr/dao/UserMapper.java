package com.lrc.ocr.dao;

import com.lrc.ocr.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {


    User queryUserByOpenid(String openid);

    void insert(User user);
}