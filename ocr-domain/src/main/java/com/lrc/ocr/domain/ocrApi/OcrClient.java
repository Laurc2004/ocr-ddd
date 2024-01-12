package com.lrc.ocr.domain.ocrApi;

import com.lrc.ocr.domain.model.aggregate.ApiResponseAggregate;
import com.lrc.ocr.domain.model.dto.OcrDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("ocr-service")
public interface OcrClient {

    @PostMapping("/ocr")
    ApiResponseAggregate getOcr(@RequestBody OcrDTO ocrDTO);
}