package com.lrc.ocr.domain.ocr.service.strategy;

import com.lrc.ocr.domain.ocr.model.aggregate.ApiResponseAggregate;
import com.lrc.ocr.domain.ocr.model.entity.OcrInputEntity;

public interface OcrStrategy {
    ApiResponseAggregate process(OcrInputEntity input);
}