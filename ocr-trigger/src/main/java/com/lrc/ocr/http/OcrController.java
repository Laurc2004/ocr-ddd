package com.lrc.ocr.http;

import com.lrc.ocr.domain.ocr.model.aggregate.ApiDataAggregate;
import com.lrc.ocr.domain.ocr.model.dto.OcrDTO;
import com.lrc.ocr.domain.ocr.model.vo.OcrTextVO;
import com.lrc.ocr.domain.ocr.service.IOcrService;
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
    public Result<OcrTextVO> getTextOnlyByFile(@RequestPart("file") MultipartFile file){
        OcrTextVO ocrText = ocrService.getText(file);
        return Result.success(ocrText);
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

    /**
     * 通过url或文件路径获取全部
     * @param ocrDTO
     * @return
     */
    @ApiOperation("通过url或文件路径获取全部")
    @PostMapping("/getTotalByUrl")
    public Result<List<ApiDataAggregate>> getTotalByUrl(@RequestBody OcrDTO ocrDTO){
        List<ApiDataAggregate> apiDataAggregate = ocrService.getTotalByUrl(ocrDTO);
        return Result.success(apiDataAggregate);
    }

    /**
     * 通过url或文件路径获取文字
     * @param ocrDTO
     * @return
     */
    @ApiOperation("通过url或文件路径仅获取文字")
    @PostMapping("/getTextByUrl")
    public Result<OcrTextVO> getTextByUrl(@RequestBody OcrDTO ocrDTO){
        OcrTextVO ocrText = ocrService.getTextByUrl(ocrDTO);
        return Result.success(ocrText);
    }

}
