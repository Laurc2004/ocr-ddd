package com.lrc.ocr.http;

import com.lrc.ocr.domain.service.IOcrService;
import com.lrc.ocr.model.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ocr")
public class OcrController {

    @Resource
    private IOcrService ocrService;

    /**
     * 上传文件获取文字
     * @param file
     * @return
     */
    @PostMapping("/getText")
    public Result<?> getTextOnly(MultipartFile file){
        ocrService.getText(file);
        return Result.success();
    }

}
