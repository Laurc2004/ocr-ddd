package com.lrc.ocr.domain.service;

import com.lrc.ocr.enums.BaseError;
import com.lrc.ocr.exception.ServiceException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public abstract class OcrTextService implements IOcrService {
    @Resource
    private MinioClient minioClient;

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.bucket}")
    private String bucket;

    /**
     * 上传图片获取仅文字
     * @param file
     */
    @Override
    public void getText(MultipartFile file) {
        String onlyFileName = getOnlyFileName(file);
        //上传 minio并返回链接
        String url = uploadToMinio(file, onlyFileName);
        log.info("上传的url为:{}" , url);

        // todo okhttp 请求Python的接口并进行处理

        // todo 存入数据库
        // 返回
    }

    /**
     * 获取文件并生成唯一名称
     * @param file
     * @return
     */
    protected String getOnlyFileName(MultipartFile file){
        // 判断文件格式是否为空或图片图片
        if (file == null || file.isEmpty()){
            throw new ServiceException(BaseError.IMAGE_ERROR);
        }
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")){
            throw new ServiceException(BaseError.FILE_ERROR);
        }
        // 采用唯一名称
        String filename = file.getOriginalFilename();
        if (StringUtils.isBlank(filename)){
            throw new ServiceException(BaseError.FILE_ERROR);
        }
        String[] split = filename.split("\\.");
        // 如果分割的不是两串或以上
        if (split.length < 2){
            throw new ServiceException(BaseError.FILE_ERROR);
        }
        // 获取后缀名
        String lastName = split[split.length - 1];
        // 拼接成唯一名称
        return UUID.randomUUID() + "." + lastName;
    }

    /**
     * 上传文件到minio
     * @param file
     * @param fileName
     * @return
     */
    protected String uploadToMinio(MultipartFile file, String fileName){
        // 上传对象到存储桶
        try (InputStream stream = file.getInputStream()) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket) // 存储器名称
                    .object(fileName) // 文件名称
                    .stream(
                            stream, file.getSize(), -1) // 文件流，文件大小，文件截止的位置（-1表示文件末尾）
//                    .contentType(file.getContentType()) // 文件类型
                    .build()
            );
        } catch (Exception e){
            throw new ServiceException(BaseError.MINIO_ERROR);
        }
        return endpoint + bucket + "/" + fileName;
    }
}
