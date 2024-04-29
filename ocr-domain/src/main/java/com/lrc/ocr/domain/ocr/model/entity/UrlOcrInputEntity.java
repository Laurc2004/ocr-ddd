package com.lrc.ocr.domain.ocr.model.entity;

import com.lrc.ocr.domain.ocr.model.valobj.OcrErrorVO;
import com.lrc.ocr.exception.ServiceException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
@Data
public class UrlOcrInputEntity extends OcrInputEntity {
    private String url;

    public UrlOcrInputEntity(String url) {
        if (StringUtils.isBlank(url)){
            throw new ServiceException(OcrErrorVO.URL_ERROR.getCode(),OcrErrorVO.URL_ERROR.getMsg());
        }
        this.url = url;
    }

    @Override
    public Object getSource() {
        return getUrl();
    }
}