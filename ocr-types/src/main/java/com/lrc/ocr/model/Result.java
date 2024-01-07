package com.lrc.ocr.model;

import com.lrc.ocr.enums.BaseError;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回数据类
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    private Integer code; //编码：200成功，其它数字为失败
    private String msg; //错误信息
    private T data; //数据

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 200;
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 200;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 404;
        return result;
    }

    public static <T> Result<T> error(BaseError baseError) {
        Result result = new Result();
        result.msg = baseError.getMsg();
        result.code = baseError.getCode();
        return result;
    }

    public static <T> Result<T> error(int code, String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = code;
        return result;
    }


}