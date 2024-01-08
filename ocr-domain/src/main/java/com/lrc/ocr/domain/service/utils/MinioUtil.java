package com.lrc.ocr.domain.service.utils;


import com.lrc.ocr.exception.ServiceException;
import com.lrc.ocr.prop.MinioProp;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;

import static com.lrc.ocr.enums.BaseError.MINIO_ERROR;

@Component
public class MinioUtil {
    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioProp minioProp;

    /**
     * 上传文件到minio并获取链接
     *
     * @param file
     * @param fileName
     * @return
     */
    public String uploadToMinio(MultipartFile file, String fileName) {
        // 上传对象到存储桶
        try (InputStream stream = file.getInputStream()) {
            minioClient.putObject(PutObjectArgs.builder()
                            .bucket(minioProp.getBucket()) // 存储器名称
                            .object(fileName) // 文件名称
                            .stream(
                                    stream, file.getSize(), -1) // 文件流，文件大小，文件截止的位置（-1表示文件末尾）
//                    .contentType(file.getContentType()) // 文件类型
                            .build()
            );
        } catch (Exception e) {
            throw new ServiceException(MINIO_ERROR.getCode(), MINIO_ERROR.getMsg());
        }
        if (minioProp.getEndpoint().charAt(minioProp.getEndpoint().length() - 1) != '/') {
            return minioProp.getEndpoint() + "/" + minioProp.getBucket() + "/" + fileName;
        }
        return minioProp.getEndpoint() + minioProp.getBucket() + "/" + fileName;
    }

}
