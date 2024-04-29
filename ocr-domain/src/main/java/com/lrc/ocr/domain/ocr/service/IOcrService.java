package com.lrc.ocr.domain.ocr.service;

import com.lrc.ocr.domain.ocr.model.entity.OcrInputEntity;

import java.util.List;

public interface IOcrService {
//    OcrTextVO getText(MultipartFile file);
//
//    List<ApiDataAggregate> getToal(MultipartFile file);
//
//    List<ApiDataAggregate> getTotalByUrl(OcrDTO reqUrl);
//
//    OcrTextVO getTextByUrl(OcrDTO reqUrl);

    List<?> processOcrAndFilter(OcrInputEntity input, boolean isAggregate);
}
