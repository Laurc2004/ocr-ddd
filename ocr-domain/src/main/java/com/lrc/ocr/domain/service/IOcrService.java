package com.lrc.ocr.domain.service;

import org.springframework.web.multipart.MultipartFile;

public interface IOcrService {
    void getText(MultipartFile file);
}
