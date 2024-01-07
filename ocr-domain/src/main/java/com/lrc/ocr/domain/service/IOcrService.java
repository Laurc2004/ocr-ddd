package com.lrc.ocr.domain.service;

import com.lrc.ocr.domain.model.aggregate.ApiDataAggregate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IOcrService {
    List<String> getText(MultipartFile file);

    List<ApiDataAggregate> getToal(MultipartFile file);
}
