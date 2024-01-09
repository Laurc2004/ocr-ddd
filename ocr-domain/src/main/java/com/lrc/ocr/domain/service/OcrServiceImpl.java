package com.lrc.ocr.domain.service;

import com.lrc.ocr.domain.model.aggregate.ApiDataAggregate;
import com.lrc.ocr.domain.model.aggregate.ApiResponseAggregate;
import com.lrc.ocr.domain.model.entity.OcrTextEntity;
import com.lrc.ocr.domain.model.valobj.OcrErrorVO;
import com.lrc.ocr.domain.model.vo.OcrTextVO;
import com.lrc.ocr.exception.ServiceException;
import com.lrc.ocr.utils.ImageLinkValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.lrc.ocr.domain.model.valobj.OcrErrorVO.URL_ERROR;

@Service
@Slf4j
public class OcrServiceImpl extends OcrFileService {


    /**
     * 上传图片获取仅文字
     *
     * @param file
     */
    @Override
    public OcrTextVO getText(MultipartFile file) {
        // 获取总的数据
        List<ApiDataAggregate> aggregateList = getToal(file);
        // 过滤并返回
        List<String> textLists = getTextOnlyListByData(aggregateList);

        return new OcrTextVO(textLists);
    }


    /**
     * 通过url仅获取文本
     * @param reqUrl
     * @return
     */
    @Override
    public OcrTextVO getTextByUrl(String reqUrl) {
        // 请求获取响应对象
        List<ApiDataAggregate> apiDataAggregates = getTotalByUrl(reqUrl);
        // 获取text
        List<String> textLists = getTextOnlyListByData(apiDataAggregates);
        return new OcrTextVO(textLists);
    }

    /**
     * 通过响应聚合数据仅获取文本
     * @param apiDataAggregates
     * @return
     */
    private List<String> getTextOnlyListByData(List<ApiDataAggregate> apiDataAggregates) {
        return apiDataAggregates.stream()
                .map(ApiDataAggregate::getOcrText) // 使用map操作来提取文本
                .map(OcrTextEntity::getText) // 如果getOcrText返回的是Optional或类似的包装类型，则需要这样提取文本
                .collect(Collectors.toList());
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
