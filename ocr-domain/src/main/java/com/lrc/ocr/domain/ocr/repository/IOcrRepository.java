package com.lrc.ocr.domain.ocr.repository;


import com.lrc.ocr.domain.ocr.model.entity.UserEntity;

public interface IOcrRepository {
    UserEntity getById(Long id);

    void updateById(UserEntity updateEntity);
}
