package com.lrc.ocr.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lrc.ocr.constants.HttpConstants;
import com.lrc.ocr.domain.model.aggregate.ApiDataAggregate;
import com.lrc.ocr.domain.model.aggregate.ApiResponseAggregate;
import com.lrc.ocr.domain.model.dto.OcrDTO;
import com.lrc.ocr.domain.model.entity.OcrTextEntity;
import com.lrc.ocr.exception.ServiceException;
import com.lrc.ocr.prop.MinioProp;
import com.lrc.ocr.prop.OcrProp;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.lrc.ocr.domain.model.valobj.OcrErrorVO.*;

@Slf4j
public abstract class OcrTextService implements IOcrService {
    @Resource
    private MinioClient minioClient;

    @Resource
    private OkHttpClient okHttpClient;

    @Resource
    private MinioProp minioProp;

    @Resource
    private OcrProp ocrProp;

    @Resource
    private ObjectMapper objectMapper;


    /**
     * 上传图片获取仅文字
     *
     * @param file
     */
    @Override
    public List<String> getText(MultipartFile file) {
        // 获取生成唯一的名称
        String onlyFileName = getOnlyFileName(file);
        //上传 minio并返回链接
        String imgUrl = uploadToMinio(file, onlyFileName);
        log.info("上传的图片url为:{}", imgUrl);

        //okhttp 请求Python的接口并进行处理
        ApiResponseAggregate response = getResponse(imgUrl);

        // 返回
        return getTextOnlyList(response);
    }

    /**
     * 获取文件并生成唯一名称
     *
     * @param file
     * @return
     */
    protected String getOnlyFileName(MultipartFile file) {
        // 判断文件格式是否为空或图片图片
        if (file == null || file.isEmpty()) {
            throw new ServiceException(IMAGE_ERROR.getCode(), IMAGE_ERROR.getMsg());
        }
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            throw new ServiceException(FILE_ERROR.getCode(), FILE_ERROR.getMsg());
        }
        // 采用唯一名称
        String filename = file.getOriginalFilename();
        if (StringUtils.isBlank(filename)) {
            throw new ServiceException(FILE_ERROR.getCode(), FILE_ERROR.getMsg());
        }
        String[] split = filename.split("\\.");
        // 如果分割的不是两串或以上
        if (split.length < 2) {
            throw new ServiceException(FILE_ERROR.getCode(), FILE_ERROR.getMsg());
        }
        // 获取后缀名
        String lastName = split[split.length - 1];
        // 拼接成唯一名称
        return UUID.randomUUID() + "." + lastName;
    }

    /**
     * 上传文件到minio
     *
     * @param file
     * @param fileName
     * @return
     */
    protected String uploadToMinio(MultipartFile file, String fileName) {
        // 上传对象到存储桶
        try (InputStream stream = file.getInputStream()) {
            minioClient.putObject(PutObjectArgs.builder()
                            .bucket(minioProp.getBucket()) // 存储器名称
                            .object(fileName) // 文件名称
                            .stream(
                                    stream, file.getSize(), -1) // 文件流，文件大小，文件截止的位置（-1表示文件末尾）
//                    .contentType(file.getContentType()) // 文件类型
                            .build()
            );
        } catch (Exception e) {
            throw new ServiceException(MINIO_ERROR.getCode(), MINIO_ERROR.getMsg());
        }
        if (minioProp.getEndpoint().charAt(minioProp.getEndpoint().length() - 1) != '/') {
            return minioProp.getEndpoint() + "/" + minioProp.getBucket() + "/" + fileName;
        }
        return minioProp.getEndpoint() + minioProp.getBucket() + "/" + fileName;
    }

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
            RequestBody requestBody = RequestBody.create(json,HttpConstants.JSON_TYPE);
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

    /**
     * todo 根据返回仅仅获取文本
     * @param response 响应值
     * @return
     */
    private List<String> getTextOnlyList(ApiResponseAggregate response){
        /*
        // 创建List
        List<String> textList = new ArrayList<>();
        for (List<ApiDataAggregate> responseDatum : response.getData()) {
            for (ApiDataAggregate responseData : responseDatum) {
                String text = responseData.getOcrText().getText();
                      textList.add(text);
            }
        }
         */


        return response.getData().stream()
                .flatMap(List::stream) // 将每个子列表转换为流
                .map(ApiDataAggregate::getOcrText) // 使用map操作来提取文本
                .map(OcrTextEntity::getText) // 如果getOcrText返回的是Optional或类似的包装类型，则需要这样提取文本
                .collect(Collectors.toList());
    }
}
