package com.lrc.ocr.domain.service;

import com.lrc.ocr.domain.model.aggregate.ApiDataAggregate;
import com.lrc.ocr.domain.model.aggregate.ApiResponseAggregate;

import com.lrc.ocr.exception.ServiceException;
import com.lrc.ocr.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.lrc.ocr.domain.model.valobj.OcrErrorVO.*;

@Slf4j
public abstract class OcrFileService extends OcrUrlService {

    @Resource
    private MinioUtil minioUtil;



    /**
     * 获取全部
     * @param file
     * @return
     */
    @Override
    public List<ApiDataAggregate> getToal(MultipartFile file) {
        // 获取生成唯一的名称
        String onlyFileName = getOnlyFileName(file);
        //上传 minio并返回链接
        String imgUrl = minioUtil.uploadToMinio(file, onlyFileName);
        log.info("上传的图片url为:{}", imgUrl);
        //okhttp 请求Python的接口并进行处理
        ApiResponseAggregate response = getResponse(imgUrl);

        // todo 存入数据库
        return response.getData().get(0);
    }

    /**
     * 获取文件并生成唯一名称
     *
     * @param file
     * @return
     */
    protected String getOnlyFileName(MultipartFile file) {
        // 判断文件格式是否为空或图片图片
        if (file == null || file.isEmpty()) {
            throw new ServiceException(IMAGE_ERROR.getCode(), IMAGE_ERROR.getMsg());
        }
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            throw new ServiceException(FILE_ERROR.getCode(), FILE_ERROR.getMsg());
        }
        // 采用唯一名称
        String filename = file.getOriginalFilename();
        if (StringUtils.isBlank(filename)) {
            throw new ServiceException(FILE_ERROR.getCode(), FILE_ERROR.getMsg());
        }
        String[] split = filename.split("\\.");
        // 如果分割的不是两串或以上
        if (split.length < 2) {
            throw new ServiceException(FILE_ERROR.getCode(), FILE_ERROR.getMsg());
        }
        // 获取后缀名
        String lastName = split[split.length - 1];
        // 拼接成唯一名称
        return UUID.randomUUID() + "." + lastName;
    }




}
