package com.lrc.ocr.domain.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文字坐标
 */
@ApiModel("文字坐标")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CoordinateEntity {
    @ApiModelProperty("x坐标")
    private double x;
    @ApiModelProperty("y坐标")
    private double y;
}