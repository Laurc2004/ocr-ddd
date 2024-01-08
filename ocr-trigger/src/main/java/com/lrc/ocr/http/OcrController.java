package com.lrc.ocr.http;

import com.lrc.ocr.domain.model.aggregate.ApiDataAggregate;
import com.lrc.ocr.domain.service.IOcrService;
import com.lrc.ocr.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "ocr请求接口")
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
    @ApiOperation("上传文件获取文字")
    @PostMapping("/getText")
    public Result<List<String>> getTextOnlyByFile(@RequestPart("file") MultipartFile file){
        List<String> text = ocrService.getText(file);
        return Result.success(text);
    }

    /**
     * 上传文件获取全部
     * @param file
     * @return
     */
    @ApiOperation("上传文件获取全部")
    @PostMapping("/getToal")
    public Result<List<ApiDataAggregate>> getTotalByFile(@RequestPart("file") MultipartFile file){
        List<ApiDataAggregate> apiDataAggregate = ocrService.getToal(file);
        return Result.success(apiDataAggregate);
    }

}
