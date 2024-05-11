package com.lrc.ocr.handle;

import com.lrc.ocr.enums.BaseError;
import com.lrc.ocr.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserSentinelResourceHandler {


    public static Result ocrResource(Throwable throwable){
        log.error("服务熔断",throwable);
        return Result.error(BaseError.SERVICE_BUSY_ERROR);
    }
}