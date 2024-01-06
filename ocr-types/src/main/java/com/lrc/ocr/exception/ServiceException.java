package com.lrc.ocr.exception;

import com.lrc.ocr.enums.BaseError;

public class ServiceException extends RuntimeException {
    /**
     * 错误码
     */
    private int code;

    /**
     * 异常信息
     */
    private String meg;


    public ServiceException(int code, String meg) {
        this.meg = meg;
        this.code = code;
    }

    public ServiceException(String meg, Throwable e) {
        super(meg, e);
        this.meg = meg;
    }

    public ServiceException(BaseError exceptionEnum) {
        this.meg = exceptionEnum.getMsg();
        this.code = exceptionEnum.getCode();
    }
}