package com.lrc.ocr.domain.ocr.service.strategy;

import com.lrc.ocr.domain.ocr.api.OcrClient;
import com.lrc.ocr.domain.ocr.model.aggregate.ApiResponseAggregate;
import com.lrc.ocr.domain.ocr.model.dto.OcrDTO;
import com.lrc.ocr.domain.ocr.model.entity.FileOcrInputEntity;
import com.lrc.ocr.domain.ocr.model.entity.OcrInputEntity;
import com.lrc.ocr.domain.ocr.model.exception.OcrServiceException;
import com.lrc.ocr.domain.ocr.model.valobj.OcrErrorVO;
import com.lrc.ocr.domain.ocr.service.FileUploadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FileOcrStrategy implements OcrStrategy {
    @Resource
    private FileUploadService fileUploadService;
    @Resource
    private OcrClient ocrClient;

    @Override
    public ApiResponseAggregate process(OcrInputEntity input) {
        if (!(input instanceof FileOcrInputEntity)) {
            throw new OcrServiceException(OcrErrorVO.STRATEGY_ERROR);
        }

        // 向下转型
        FileOcrInputEntity fileInput = (FileOcrInputEntity) input;
        String imgUrl = fileUploadService.uploadToUrl(fileInput.getFile());
        OcrDTO ocrDTO = new OcrDTO(imgUrl);
        return ocrClient.getOcr(ocrDTO);
    }
}