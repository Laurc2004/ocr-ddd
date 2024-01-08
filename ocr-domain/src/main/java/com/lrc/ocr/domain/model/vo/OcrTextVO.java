package com.lrc.ocr.domain.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel("ocr识别文本返回")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OcrTextVO {
    @ApiModelProperty("ocr识别文本集合")
    private List<String> ocrTextList;
}
