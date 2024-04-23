package com.lrc.ocr.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 
 * @TableName ocr识别结果表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OcrResult implements Serializable {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 图片链接
     */
    private String imageUrl;

    /**
     * ocr识别的文字结果
     */
    private String textResult;
    /**
     * 逻辑删除 1为已删除
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}