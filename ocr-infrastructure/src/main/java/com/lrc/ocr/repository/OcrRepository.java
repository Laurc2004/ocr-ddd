package com.lrc.ocr.repository;

import com.lrc.ocr.dao.UserMapper;
import com.lrc.ocr.domain.ocr.model.entity.UserEntity;
import com.lrc.ocr.domain.ocr.repository.IOcrRepository;
import com.lrc.ocr.po.User;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class OcrRepository implements IOcrRepository {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserEntity getById(Long id) {
        User user = userMapper.getById(id);
        return new UserEntity(user.getId(), user.getOpenid(), user.getLines());
    }

    @Override
    public void updateById(UserEntity updateEntity) {
        User user = new User();
        user.setId(updateEntity.getId());
        user.setOpenid(updateEntity.getOpenid());
        user.setLines(updateEntity.getLines());
        userMapper.updateById(user);
    }
}
