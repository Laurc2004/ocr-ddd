package com.lrc.ocr.domain.ocr.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OcrEnglishService extends OcrService{

    @Override
    protected List<String> filter(List<String> texts) {
        return texts.stream()
                .map(s -> s.replaceAll("[^a-zA-Z0-9\\s]", ""))
                .collect(Collectors.toList());
    }
}
