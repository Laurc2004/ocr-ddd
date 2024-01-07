package com.lrc.ocr.http;

import com.lrc.ocr.domain.model.aggregate.ApiDataAggregate;
import com.lrc.ocr.domain.service.IOcrService;
import com.lrc.ocr.model.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

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
    public Result<List<String>> getTextOnlyByFile(MultipartFile file){
        List<String> text = ocrService.getText(file);
        return Result.success(text);
    }

    /**
     * 上传文件获取全部
     * @param file
     * @return
     */
    @PostMapping("/getToal")
    public Result<List<ApiDataAggregate>> getTotalByFile(MultipartFile file){
        List<ApiDataAggregate> apiDataAggregate = ocrService.getToal(file);
        return Result.success(apiDataAggregate);
    }

}
