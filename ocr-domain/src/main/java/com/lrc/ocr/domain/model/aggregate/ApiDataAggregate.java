package com.lrc.ocr.domain.model.aggregate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lrc.ocr.domain.model.entity.CoordinateEntity;
import com.lrc.ocr.domain.model.entity.OcrTextEntity;
import com.lrc.ocr.domain.model.jsonSerializer.ApiDataAggregateDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 聚合数据
 */
@ApiModel("聚合数据")
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = ApiDataAggregateDeserializer.class)
public class ApiDataAggregate {
    @ApiModelProperty("聚合坐标")
    private List<CoordinateEntity> coordinates;
    @ApiModelProperty("聚合文本和可信度")
    private OcrTextEntity ocrText;
}