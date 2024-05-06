package com.lrc.ocr.domain.ocr.service;

import com.lrc.ocr.exception.ServiceException;
import com.lrc.ocr.utils.MinioUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;

import static com.lrc.ocr.enums.BaseError.FILE_ERROR;
import static com.lrc.ocr.enums.BaseError.IMAGE_ERROR;

@Service
public class FileUploadService {

    @Resource
    private MinioUtil minioUtil;

    /**
     * 上传文件转URL
     * @param file
     * @return
     */
    public String uploadToUrl(MultipartFile file){
        String onlyFileName = getOnlyFileName(file);
        return minioUtil.uploadToMinio(file, onlyFileName);
    }

    /**
     * 获取文件并生成唯一名称
     *
     * @param file
     * @return
     */
    private String getOnlyFileName(MultipartFile file) {
        // 判断文件格式是否为空或图片图片
        if (file == null || file.isEmpty()) {
            throw new ServiceException(IMAGE_ERROR);
        }
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            throw new ServiceException(FILE_ERROR);
        }
        // 采用唯一名称
        String filename = file.getOriginalFilename();
        if (StringUtils.isBlank(filename)) {
            throw new ServiceException(FILE_ERROR);
        }
        String[] split = filename.split("\\.");
        // 如果分割的不是两串或以上
        if (split.length < 2) {
            throw new ServiceException(FILE_ERROR);
        }
        // 获取后缀名
        String lastName = split[split.length - 1];
        // 拼接成唯一名称
        return UUID.randomUUID() + "." + lastName;
    }
}
