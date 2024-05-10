package com.lrc.ocr.domain.ocr.model.valobj;

import lombok.AllArgsConstructor;

/**
 * ocr业务异常枚举类
 */
@AllArgsConstructor
public enum OcrErrorVO  {

    IMAGE_ERROR(100,"图片错误"),

    FILE_ERROR(102,"文件格式有误"),
    STRATEGY_ERROR(0,"策略异常"),
    SQL_ERROR(103,"SQL异常"),
    ALREADY_EXISTS(104,"用户已存在"),
    HTTP_ERROR(105,"OCR接口异常"),
    URL_ERROR(106,"图片URL异常"),
    USER_ERROR(201,"用户名或密码不能为空"),
    USER_LOGIN_ERROR(202,"用户名或密码错误"),
    USER_LINE_ERROR(203,"用户余额不足"),
    ;
    private final Integer code; //编码：200成功，其它数字为失败
    private final String msg; //错误信息

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
