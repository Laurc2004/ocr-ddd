package com.lrc.ocr.domain.user.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginUserVO implements Serializable {
    /**
     * 用户token
     */
    private String token;


    private static final long serialVersionUID = 1L;
}
