package com.lrc.ocr.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
@Slf4j
public class RedisConfig {

    private int database;
    private String host;
    private int port;
    private String password;

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        // 添加单机的redission配置
        config.useSingleServer()
                .setAddress("redis://" + host + ":" + port)
                .setPassword(password)
                .setDatabase(database);

        log.info("连接redission...");

        return Redisson.create(config);
    }
}