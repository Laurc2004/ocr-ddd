package com.lrc.ocr.domain.service;

import com.lrc.ocr.domain.model.aggregate.ApiDataAggregate;
import com.lrc.ocr.domain.model.aggregate.ApiResponseAggregate;
import com.lrc.ocr.domain.model.entity.OcrTextEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OcrServiceImpl extends OcrTextService{


    /**
     * 上传图片获取仅文字
     *
     * @param file
     */
    @Override
    public List<String> getText(MultipartFile file) {
        // 获取生成唯一的名称
        String onlyFileName = getOnlyFileName(file);
        //上传 minio并返回链接
        String imgUrl = uploadToMinio(file, onlyFileName);
        log.info("上传的图片url为:{}", imgUrl);

        //okhttp 请求Python的接口并进行处理
        ApiResponseAggregate response = getResponse(imgUrl);

        // 返回
        return getTextOnlyList(response);
    }

    /**
     * @param response 响应值
     * @return
     */
    private List<String> getTextOnlyList(ApiResponseAggregate response){
        /*
        // 创建List
        List<String> textList = new ArrayList<>();
        for (List<ApiDataAggregate> responseDatum : response.getData()) {
            for (ApiDataAggregate responseData : responseDatum) {
                String text = responseData.getOcrText().getText();
                      textList.add(text);
            }
        }
         */


        return response.getData().stream()
                .flatMap(List::stream) // 将每个子列表转换为流
                .map(ApiDataAggregate::getOcrText) // 使用map操作来提取文本
                .map(OcrTextEntity::getText) // 如果getOcrText返回的是Optional或类似的包装类型，则需要这样提取文本
                .collect(Collectors.toList());
    }
}
