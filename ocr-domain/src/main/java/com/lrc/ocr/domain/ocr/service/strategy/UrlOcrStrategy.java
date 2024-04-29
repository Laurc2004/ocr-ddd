package com.lrc.ocr.domain.ocr.service.strategy;

import com.lrc.ocr.domain.ocr.api.OcrClient;
import com.lrc.ocr.domain.ocr.model.aggregate.ApiResponseAggregate;
import com.lrc.ocr.domain.ocr.model.dto.OcrDTO;
import com.lrc.ocr.domain.ocr.model.entity.OcrInputEntity;
import com.lrc.ocr.domain.ocr.model.entity.UrlOcrInputEntity;
import com.lrc.ocr.domain.ocr.model.exception.OcrServiceException;
import com.lrc.ocr.domain.ocr.model.valobj.OcrErrorVO;
import com.lrc.ocr.utils.ImageLinkValidator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UrlOcrStrategy implements OcrStrategy {
    @Resource
    private OcrClient ocrClient;

    @Override
    public ApiResponseAggregate process(OcrInputEntity input) {
        if (!(input instanceof UrlOcrInputEntity)) {
            throw new OcrServiceException(OcrErrorVO.STRATEGY_ERROR);
        }
        // 向下转型
        UrlOcrInputEntity urlInput = (UrlOcrInputEntity) input;
        String url = urlInput.getUrl();
        if (!ImageLinkValidator.isImageLink(url)) {
            throw new OcrServiceException(OcrErrorVO.IMAGE_ERROR);
        }
        OcrDTO ocrDTO = new OcrDTO(url);
        return ocrClient.getOcr(ocrDTO);
    }
}