package com.lrc.ocr.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lrc.ocr.constants.HttpConstants;
import com.lrc.ocr.domain.model.aggregate.ApiDataAggregate;
import com.lrc.ocr.domain.model.aggregate.ApiResponseAggregate;
import com.lrc.ocr.domain.model.dto.OcrDTO;
import com.lrc.ocr.domain.model.vo.OcrTextVO;
import com.lrc.ocr.exception.ServiceException;
import com.lrc.ocr.prop.MinioProp;
import com.lrc.ocr.prop.OcrProp;
import okhttp3.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

import static com.lrc.ocr.domain.model.valobj.OcrErrorVO.HTTP_ERROR;

public abstract class OcrUrlService implements IOcrService{

    @Resource
    private OkHttpClient okHttpClient;

    @Resource
    private OcrProp ocrProp;

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 通过url获取全部
     * @param reqUrl
     * @return
     */
    @Override
    public List<ApiDataAggregate> getTotalByUrl(String reqUrl) {
        // 请求响应
        ApiResponseAggregate response = getResponse(reqUrl);
        // todo 存入数据库
        return response.getData().get(0);
    }


    public abstract OcrTextVO getTextByUrl(String reqUrl) ;

    /**
     * 创建请求ocr接口并返回响应对象
     * @param imgUrl 图片链接
     * @return Api对象
     */
    protected ApiResponseAggregate getResponse(String imgUrl){
        ApiResponseAggregate responseValues = null;

        try {
            // 创建OcrDTO对象
            OcrDTO ocrDTO = new OcrDTO(imgUrl);

            // Java对象转化为JSON字符串（“序列化”）
            String json = objectMapper.writeValueAsString(ocrDTO);

            // 创建JSON格式
            RequestBody requestBody = RequestBody.create(json, HttpConstants.JSON_TYPE);
            // 创建请求格式
            Request request = new Request.Builder()
                    .post(requestBody)
                    .url(ocrProp.getUrl())
                    .build();

            // 发送并接收响应
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();

            responseValues = responseToObject(response);

        } catch (Exception e) {
            throw new ServiceException(HTTP_ERROR.getCode(),HTTP_ERROR.getMsg());
        }


        return responseValues;
    }

    /**
     * 返回结果转换为Java对象
     * @param response
     * @return
     */
    private ApiResponseAggregate responseToObject(Response response) throws IOException {
        // 校验是否成功响应
        if (!response.isSuccessful()){
            throw new ServiceException(HTTP_ERROR.getCode(),HTTP_ERROR.getMsg());
        }
        // 获取body
        ResponseBody body = response.body();
        // 校验body是否为空
        if (body == null){
            throw new ServiceException(HTTP_ERROR.getCode(),HTTP_ERROR.getMsg());
        }
        // 获取响应的JSON
        String responseJson = body.string();
        // 使用Jackson对象进行反序列化
        ApiResponseAggregate responseValues = objectMapper.readValue(responseJson, ApiResponseAggregate.class);
        // 判断responseValues返回不能为空
        if (responseValues == null){
            throw new ServiceException(HTTP_ERROR.getCode(),HTTP_ERROR.getMsg());
        }
        // 判断请求返回的code参数是否为指定的200，否则抛出异常，并返回请求的message信息
        if (responseValues.getCode() != 200) {
            throw new ServiceException(HTTP_ERROR.getCode(),responseValues.getMessage());
        }

        return responseValues;
    }
}
