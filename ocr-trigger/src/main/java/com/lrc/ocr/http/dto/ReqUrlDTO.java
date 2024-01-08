package com.lrc.ocr.http.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求参数
 */
@ApiModel("请求参数")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqUrlDTO {
    @ApiModelProperty("请求url")
    private String reqUrl;
}
