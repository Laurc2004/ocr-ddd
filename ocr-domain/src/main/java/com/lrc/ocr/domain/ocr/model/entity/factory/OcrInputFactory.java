package com.lrc.ocr.domain.ocr.model.entity.factory;

import com.lrc.ocr.domain.ocr.model.entity.FileOcrInputEntity;
import com.lrc.ocr.domain.ocr.model.entity.OcrInputEntity;
import com.lrc.ocr.domain.ocr.model.entity.UrlOcrInputEntity;
import org.springframework.web.multipart.MultipartFile;

public class OcrInputFactory {
    public static OcrInputEntity createFromFile(MultipartFile file) {
        return new FileOcrInputEntity(file);
    }

    public static OcrInputEntity createFromUrl(String url) {
        return new UrlOcrInputEntity(url);
    }
}