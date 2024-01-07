package com.lrc.ocr.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OcrTextEntity {
    private String text;
    private double score;
}