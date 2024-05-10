package com.lrc.ocr.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信公众号的配置
 */
@ConfigurationProperties(prefix = "wechat.pay")
@Component
@Data
public class WechatPayProp {
    private String apiV3Key;
    private String mchId;
    private String mchSerialNo;
    private String privateKeyFilePath;
    private String notifyUrl;
    private String appid;
}
