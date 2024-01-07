package com.lrc.ocr.config;

import com.lrc.ocr.exception.ServiceException;
import com.lrc.ocr.prop.MinioProp;
import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

import static com.lrc.ocr.enums.BaseError.MINIO_ERROR;


@Configuration
@Data
@Slf4j
public class MinioConfig {

    @Resource
    private MinioProp minioProp;

    /**
     * 创建Minio对象
     */
    @Bean
    public MinioClient minioClient(){
        log.info("Minio配置开始...");
        MinioClient client = null;
        try {
            client = MinioClient.builder()
                    .endpoint(minioProp.getEndpoint())
                    .credentials(minioProp.getUsername(), minioProp.getPassword())
                    .build();

            boolean isExist = client.bucketExists(BucketExistsArgs.builder().bucket(minioProp.getBucket()).build());
            if (isExist) {
                log.info("minio存储桶存在");  // 存储桶存在
            } else {
                log.error("minio存储桶不存在");// 存储桶不存在
                throw new ServiceException(MINIO_ERROR);
            }
        } catch (Exception e){
            log.error("minio创建失败",e);
        }
        if (client == null){
            throw new ServiceException(MINIO_ERROR);
        }
        return client;
    }


}
