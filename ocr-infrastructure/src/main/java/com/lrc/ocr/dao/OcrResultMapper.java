package com.lrc.ocr.dao;

import com.lrc.ocr.po.OcrResult;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OcrResultMapper {

    int deleteByPrimaryKey(Long id);

    int insert(OcrResult record);

    int insertSelective(OcrResult record);

    OcrResult selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OcrResult record);

    int updateByPrimaryKey(OcrResult record);

}