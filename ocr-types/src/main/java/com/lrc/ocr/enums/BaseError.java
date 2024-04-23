package com.lrc.ocr.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BaseError {
    MINIO_ERROR(101,"文件存储异常"),
    TOKEN_ERROR(201,"解析token异常"),
    LOGIN_USER_NOT_LOGIN_ERROR(202,"用户未登录"),
    CODE_ERROR(203,"验证码不正确"),
    LOGIN_ERROR(204,"用户登录异常"),

    PARAM_ERROR(501, "参数有误"),
    UNKNOWN_ERROR(404,"未知SQL异常"),
    SQL_ERROR(405,"SQL异常，信息为:");
    private final Integer code; //编码：200成功，其它数字为失败
    private final String msg; //错误信息

}
