package com.lrc.ocr.domain.ocr.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OcrDefaultService extends OcrService{
    @Override
    protected List<String> filter(List<String> texts) {
        return texts;
    }
}
