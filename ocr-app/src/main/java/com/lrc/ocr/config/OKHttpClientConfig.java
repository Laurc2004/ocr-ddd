package com.lrc.ocr.config;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class OKHttpClientConfig {
    @Bean
    public OkHttpClient okHttpClient(){

        return new OkHttpClient.Builder()
                .connectTimeout(60,TimeUnit.SECONDS)        // 设置连接超时
                .readTimeout(60, TimeUnit.SECONDS)          //设置读超时
                .writeTimeout(60, TimeUnit.SECONDS)          //设置写超时
                .build();

    }
}
