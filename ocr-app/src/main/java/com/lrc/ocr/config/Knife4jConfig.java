package com.lrc.ocr.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Slf4j
public class Knife4jConfig {

    /**
     * 通过knife4j生成接口文档
     * @return
     */
    @Bean
    public Docket docket() {
        log.info("接口文档初始化...");
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("OCR项目接口文档")
                .version("1.0.0")
                .description("OCR项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lrc.ocr.http"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
