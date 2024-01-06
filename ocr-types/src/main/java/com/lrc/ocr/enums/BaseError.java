package com.lrc.ocr.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor

public enum BaseError {
    PARAM_ERROR(501, "参数有误"),
    IMAGE_ERROR(502,"图片错误"),
    MINIO_ERROR(503,"文件存储异常"),
    FILE_ERROR(503,"文件格式有误"),
    SQL_ERROR(400,"SQL异常"),
    ALREADY_EXISTS(402,"用户已存在"),
    UNKNOWN_ERROR(404,"未知异常");
    private final Integer code; //编码：200成功，其它数字为失败
    private final String msg; //错误信息

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
