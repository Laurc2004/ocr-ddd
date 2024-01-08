package com.lrc.ocr.domain.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ocr文本和可信度
 */
@ApiModel("ocr文本和可信度")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OcrTextEntity {
    @ApiModelProperty("ocr文本")
    private String text;
    @ApiModelProperty("可信度")
    private double score;
}