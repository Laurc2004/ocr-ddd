package com.lrc.ocr.domain.model.valobj;

import com.lrc.ocr.enums.BaseError;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OcrErrorVO  {

    IMAGE_ERROR(100,"图片错误"),
    MINIO_ERROR(101,"文件存储异常"),
    FILE_ERROR(102,"文件格式有误"),
    SQL_ERROR(103,"SQL异常"),
    ALREADY_EXISTS(104,"用户已存在"),
    HTTP_ERROR(105,"OCR接口异常");
    private final Integer code; //编码：200成功，其它数字为失败
    private final String msg; //错误信息

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
