package com.lrc.ocr.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "ocr")
@Component
@Data
public class OcrProp {
    /**
     * 请求的第三方ocr服务url
     */
    private String url = "http://127.0.0.1:8888/ocr/";
}
