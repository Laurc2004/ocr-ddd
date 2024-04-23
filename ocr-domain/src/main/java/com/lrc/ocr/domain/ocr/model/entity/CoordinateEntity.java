package com.lrc.ocr.domain.ocr.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文字坐标
 */
@ApiModel("文字坐标")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CoordinateEntity implements Serializable {
    @ApiModelProperty("x坐标")
    private double x;
    @ApiModelProperty("y坐标")
    private double y;
}