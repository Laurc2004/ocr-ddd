package com.lrc.ocr.domain.ocr.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class UserEntity implements Serializable {
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
//    /**
//     * 验证码
//     */
//    private String code;


}