package com.lrc.ocr.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 
 * @TableName 用户表
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class User implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 微信用户唯一标识
     */
    private String openid;

    /**
     * 用户额度
     */
    private Integer lines;

    private static final long serialVersionUID = 1L;
}