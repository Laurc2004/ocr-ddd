package com.lrc.ocr.handle;

import com.lrc.ocr.exception.ServiceException;
import com.lrc.ocr.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

import static com.lrc.ocr.enums.BaseError.SQL_ERROR;
import static com.lrc.ocr.enums.BaseError.UNKNOWN_ERROR;


/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(ServiceException ex){
        log.error("错误码：{},异常信息：{},message为：{}",  ex.getCode(),ex.getMessage(),ex.getMeg());
        return Result.error(ex.getCode(),ex.getMeg());
    }

    /**
     * 捕获SQL异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error("异常信息：{}",ex.getMessage());
        String message = ex.getMessage();
//        if (message.contains("Duplicate entry")){
//            String[] s = message.split(" ");
//            String name = s[2];
//            return Result.error(name + ALREADY_EXISTS);
//        }
        if (message != null){
            return Result.error(SQL_ERROR.getCode(), SQL_ERROR.getMsg() + message);
        }
        return Result.error(UNKNOWN_ERROR);
    }

}