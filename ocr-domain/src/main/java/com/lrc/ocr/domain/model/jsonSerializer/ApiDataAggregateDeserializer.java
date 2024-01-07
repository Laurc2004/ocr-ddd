package com.lrc.ocr.domain.model.jsonSerializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lrc.ocr.domain.model.aggregate.ApiDataAggregate;
import com.lrc.ocr.domain.model.entity.CoordinateEntity;
import com.lrc.ocr.domain.model.entity.OcrTextEntity;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// 自定义的反序列化器，用于将JSON数据解析为ApiDataAggregate对象
public class ApiDataAggregateDeserializer extends JsonDeserializer<ApiDataAggregate> {
    @Resource
    private ObjectMapper objectMapper;
    @Override
    public ApiDataAggregate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // 赋值objectMapper实例解析JSON数据
        objectMapper = (ObjectMapper) p.getCodec();
        // 读取JSON数据并将其转换为JsonNode对象
        JsonNode node = objectMapper.readTree(p);

        // 创建一个列表，用于存储坐标实体
        List<CoordinateEntity> coordinates = new ArrayList<>();
        // 遍历坐标节点
        for (JsonNode coordinateNode : node.get(0)) {
            // 从节点中获取x和y坐标
            double x = coordinateNode.get(0).asDouble();
            double y = coordinateNode.get(1).asDouble();
            // 创建一个新的坐标实体并将其添加到列表中
            coordinates.add(new CoordinateEntity(x, y));
        }

        // 获取OCR文本节点
        JsonNode ocrTextNode = node.get(1);
        // 从节点中获取文本和得分
        String text = ocrTextNode.get(0).asText();
        double score = ocrTextNode.get(1).asDouble();
        // 创建一个新的OCR文本实体
        OcrTextEntity ocrText = new OcrTextEntity(text, score);

        // 创建一个新的ApiDataAggregate对象并返回
        return new ApiDataAggregate(coordinates, ocrText);
    }
}

