package com.lrc.ocr.domain.model.aggregate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 请求接口响应
 */
@ApiModel("接口响应")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponseAggregate {
    @ApiModelProperty("接口响应码")
    private int code;
    @ApiModelProperty("聚合数据")
    private List<List<ApiDataAggregate>> data;
    @ApiModelProperty("响应信息")
    private String message;
}
