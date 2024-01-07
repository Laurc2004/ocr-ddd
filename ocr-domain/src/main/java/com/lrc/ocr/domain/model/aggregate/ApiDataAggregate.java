package com.lrc.ocr.domain.model.aggregate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lrc.ocr.domain.model.entity.CoordinateEntity;
import com.lrc.ocr.domain.model.entity.OcrTextEntity;
import com.lrc.ocr.domain.model.jsonSerializer.ApiDataAggregateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = ApiDataAggregateDeserializer.class)
public class ApiDataAggregate {
    private List<CoordinateEntity> coordinates;
    private OcrTextEntity ocrText;
}