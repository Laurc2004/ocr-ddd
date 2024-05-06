package com.lrc.ocr.domain.ocr.service;

import com.lrc.ocr.domain.ocr.model.exception.OcrServiceException;
import com.lrc.ocr.domain.ocr.model.valobj.FilterConstants;
import com.lrc.ocr.domain.ocr.model.valobj.OcrErrorVO;
import com.lrc.ocr.domain.ocr.service.impl.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class FilterStrategyFactory {
    // 使用Spring的ApplicationContext来获取Bean
    private final ApplicationContext context;

    public FilterStrategyFactory(ApplicationContext context) {
        this.context = context;
    }

    public OcrService createFilterStrategy(String filterType) {
        // 根据filterType 选择并返回相应的OcrService子类实例
        if (FilterConstants.DEFAULT.equals(filterType) || StringUtils.isBlank(filterType)) {
            return context.getBean(OcrDefaultService.class);
        } else if (FilterConstants.CAR.equals(filterType)) {
            return context.getBean(OcrCarService.class);
        } else if (FilterConstants.ID.equals(filterType)) {
            return context.getBean(OcrIDService.class);
        } else if (FilterConstants.ENGLISH.equals(filterType)) {
            return context.getBean(OcrEnglishService.class);
        }
        throw new OcrServiceException(OcrErrorVO.STRATEGY_ERROR);
    }
}