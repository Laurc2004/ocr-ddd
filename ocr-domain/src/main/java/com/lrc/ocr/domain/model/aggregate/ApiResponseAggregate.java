package com.lrc.ocr.domain.model.aggregate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponseAggregate {
    private int code;
    private List<List<ApiDataAggregate>> data;
    private String message;
}
