package com.lrc.ocr.http;

import com.lrc.ocr.domain.ocr.model.entity.OcrInputEntity;
import com.lrc.ocr.domain.ocr.model.entity.factory.OcrInputFactory;
import com.lrc.ocr.domain.ocr.service.IOcrService;
import com.lrc.ocr.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
     *
     * @param file 文件
     * @param isAggregate 聚合对象还是纯文本
     * @return List<聚合对象或纯文本>
     */
    @ApiOperation("上传文件获取结果")
    @PostMapping("/getByFile")
    public Result<List<?>> getTextOnlyByFile(@RequestPart("file") MultipartFile file, boolean isAggregate){
        OcrInputEntity fromFile = OcrInputFactory.createFromFile(file);
        List<?> list = ocrService.processOcrAndFilter(fromFile, isAggregate);
        return Result.success(list);
    }


    /**
     *
     * @param url 图片链接
     * @param isAggregate 聚合对象还是纯文本
     * @return List<聚合对象或纯文本>
     */
    @ApiOperation("通过url获取结果")
    @PostMapping("/getTotalByUrl")
    public Result<List<?>> getTotalByUrl(String url, boolean isAggregate){
        OcrInputEntity fromUrl = OcrInputFactory.createFromUrl(url);
        List<?> list = ocrService.processOcrAndFilter(fromUrl, isAggregate);
        return Result.success(list);
    }

}
