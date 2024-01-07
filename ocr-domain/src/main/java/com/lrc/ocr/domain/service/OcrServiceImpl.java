package com.lrc.ocr.domain.service;

import com.lrc.ocr.domain.model.aggregate.ApiDataAggregate;
import com.lrc.ocr.domain.model.aggregate.ApiResponseAggregate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
public class OcrServiceImpl extends OcrTextService{
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
        String imgUrl = uploadToMinio(file, onlyFileName);
        log.info("上传的图片url为:{}", imgUrl);
        //okhttp 请求Python的接口并进行处理
        ApiResponseAggregate response = getResponse(imgUrl);

        // todo 存入数据库
        return response.getData().get(0);
    }
}
