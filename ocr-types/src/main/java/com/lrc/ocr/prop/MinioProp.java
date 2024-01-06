package com.lrc.ocr.prop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Component;

/**
 * minio的yml配置
 */
@ConfigurationProperties(prefix = "minio")
@Component
@Data
public class MinioProp {
    /**
     * 节点url
     */
    private String endpoint = "http://127.0.0.1:9000/";
    /**
     * 用户名
     */
    private String username = "minioadmin";
    /**
     * 密码
     */
    private String password = "minioadmin";
    /**
     * 存储桶
     */
    private String bucket;
}
