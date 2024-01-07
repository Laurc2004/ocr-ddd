package com.lrc.ocr.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BaseError {
    MINIO_ERROR(101,"文件存储异常"),
    PARAM_ERROR(501, "参数有误"),
    UNKNOWN_ERROR(404,"未知SQL异常"),
    SQL_ERROR(405,"SQL异常，信息为:");
    private final Integer code; //编码：200成功，其它数字为失败
    private final String msg; //错误信息

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
