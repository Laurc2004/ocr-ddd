package com.lrc.ocr.domain.ocr.service.impl;

import com.lrc.ocr.domain.ocr.model.aggregate.ApiDataAggregate;
import com.lrc.ocr.domain.ocr.model.aggregate.ApiResponseAggregate;
import com.lrc.ocr.domain.ocr.model.entity.OcrInputEntity;
import com.lrc.ocr.domain.ocr.model.entity.OcrTextEntity;
import com.lrc.ocr.domain.ocr.model.entity.UserEntity;
import com.lrc.ocr.domain.ocr.model.exception.OcrServiceException;
import com.lrc.ocr.domain.ocr.model.valobj.OcrErrorVO;
import com.lrc.ocr.domain.ocr.repository.IOcrRepository;
import com.lrc.ocr.domain.ocr.service.IOcrService;
import com.lrc.ocr.domain.ocr.service.OcrStrategyFactory;
import com.lrc.ocr.domain.ocr.service.strategy.OcrStrategy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public abstract class OcrService implements IOcrService {
    @Resource
    private OcrStrategyFactory ocrStrategyFactory;
    @Resource
    private IOcrRepository ocrRepository;

    /**
     * 调用策略工厂判断输入的是URL还是文件
     * @param input
     * @param isAggregate
     * @return
     */
    @Transactional
    public List<?> processOcrAndFilter(OcrInputEntity input, boolean isAggregate){
        // 校验额度
        checkLines();
        // 交给Ocr服务处理
        ApiResponseAggregate apiResponseAggregate = doOcrService(input);
        List<ApiDataAggregate> apiDataAggregates = apiResponseAggregate.getData().get(0);
        // 返回要聚合数据还是文本
        if (isAggregate){
            return apiDataAggregates;
        }

        List<String> textOnlyListByData = getTextOnlyListByData(apiDataAggregates);
        // 如果返回是文本，通过什么过滤器去处理
        return filter(textOnlyListByData);

    }


    /**
     * 校验额度
     */
    private void checkLines(){
        // 校验当前额度
        String id = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = ocrRepository.getById(Long.parseLong(id));
        // 校验用户
        if (userEntity == null || userEntity.getLines() <= 0) {
            throw new OcrServiceException(OcrErrorVO.USER_LINE_ERROR);
        }
        UserEntity updateEntity = userEntity.setLines(userEntity.getLines() - 1);
        ocrRepository.updateById(updateEntity);
    }


    /**
     * 根据类型调用ocr服务处理
     * @param input
     * @return
     */
    private ApiResponseAggregate doOcrService(OcrInputEntity input){
        OcrStrategy strategy = ocrStrategyFactory.createStrategy(input);
        ApiResponseAggregate apiResponseAggregate = strategy.process(input);
        return apiResponseAggregate;
    }

    /**
     * 通过响应聚合数据仅获取文本
     * @param apiDataAggregates
     * @return
     */
    private List<String> getTextOnlyListByData(List<ApiDataAggregate> apiDataAggregates) {
        return apiDataAggregates.stream()
                .map(ApiDataAggregate::getOcrText) // 使用map操作来提取文本
                .map(OcrTextEntity::getText)
                .collect(Collectors.toList());
    }

    /**
     * 使用正则表达式进行过滤
     * @param texts
     * @return
     */

    protected abstract List<String> filter(List<String> texts);


}
