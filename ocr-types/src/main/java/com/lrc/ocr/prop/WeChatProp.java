package com.lrc.ocr.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信公众号的配置
 */
@ConfigurationProperties(prefix = "wechat.config")
@Component
@Data
public class WeChatProp {
    private String originalId;
    private String appId;
    private String token;
}
