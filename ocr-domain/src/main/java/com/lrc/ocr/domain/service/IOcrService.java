package com.lrc.ocr.domain.service;

import com.lrc.ocr.domain.model.aggregate.ApiDataAggregate;
import com.lrc.ocr.domain.model.vo.OcrTextVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IOcrService {
    OcrTextVO getText(MultipartFile file);

    List<ApiDataAggregate> getToal(MultipartFile file);

    List<ApiDataAggregate> getTotalByUrl(String reqUrl);

    OcrTextVO getTextByUrl(String reqUrl);
}
