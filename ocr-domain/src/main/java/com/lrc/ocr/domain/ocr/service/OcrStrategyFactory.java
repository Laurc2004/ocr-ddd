package com.lrc.ocr.domain.ocr.service;

import com.lrc.ocr.domain.ocr.model.entity.FileOcrInputEntity;
import com.lrc.ocr.domain.ocr.model.entity.OcrInputEntity;
import com.lrc.ocr.domain.ocr.model.entity.UrlOcrInputEntity;
import com.lrc.ocr.domain.ocr.model.exception.OcrServiceException;
import com.lrc.ocr.domain.ocr.model.valobj.OcrErrorVO;
import com.lrc.ocr.domain.ocr.service.strategy.FileOcrStrategy;
import com.lrc.ocr.domain.ocr.service.strategy.OcrStrategy;
import com.lrc.ocr.domain.ocr.service.strategy.UrlOcrStrategy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OcrStrategyFactory {
    @Resource
    private FileOcrStrategy fileOcrStrategy;
    @Resource
    private UrlOcrStrategy urlOcrStrategy;

    public OcrStrategy createStrategy(OcrInputEntity input) {
        if (input instanceof FileOcrInputEntity) {
            return fileOcrStrategy;
        } else if (input instanceof UrlOcrInputEntity) {
            return urlOcrStrategy;
        } else {
            throw new OcrServiceException(OcrErrorVO.STRATEGY_ERROR);
        }
    }
}