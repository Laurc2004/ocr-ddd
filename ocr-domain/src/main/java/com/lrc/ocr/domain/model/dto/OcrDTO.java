package com.lrc.ocr.domain.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装图片链接
 */
@ApiModel("封装图片链接")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OcrDTO {
    @ApiModelProperty("封装图片链接")
    private String imgUrl;
}
