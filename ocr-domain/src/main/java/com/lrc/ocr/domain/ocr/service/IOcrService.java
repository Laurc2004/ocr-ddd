package com.lrc.ocr.domain.ocr.service;

import com.lrc.ocr.domain.ocr.model.aggregate.ApiDataAggregate;
import com.lrc.ocr.domain.ocr.model.dto.OcrDTO;
import com.lrc.ocr.domain.ocr.model.vo.OcrTextVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IOcrService {
    OcrTextVO getText(MultipartFile file);

    List<ApiDataAggregate> getToal(MultipartFile file);

    List<ApiDataAggregate> getTotalByUrl(OcrDTO reqUrl);

    OcrTextVO getTextByUrl(OcrDTO reqUrl);
}
