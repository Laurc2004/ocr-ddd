package com.lrc.ocr.config;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
 
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
 
@Configuration
@Slf4j
public class JacksonConfig {
 
    @Bean("objectMapper")
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper getObjectMapper(Jackson2ObjectMapperBuilder builder) {
        log.info("配置Jackson...");
        ObjectMapper mapper = builder.build();
        
        // 日期格式
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        
        //GMT+8
        //map.put("CTT", "Asia/Shanghai");
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        
        // Include.NON_NULL 属性为NULL 不序列化
        //ALWAYS // 默认策略，任何情况都执行序列化
        //NON_EMPTY // null、集合数组等没有内容、空字符串等，都不会被序列化
        //NON_DEFAULT // 如果字段是默认值，就不会被序列化
        //NON_ABSENT // null的不会序列化，但如果类型是AtomicReference，依然会被序列化
        mapper.setSerializationInclusion(Include.NON_NULL);
        
        //允许字段名没有引号（可以进一步减小json体积）：
        mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        
        //允许单引号：
        mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        
        // 允许出现特殊字符和转义符
        //mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);这个已经过时。
        mapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        
        //允许C和C++样式注释：
        mapper.configure(Feature.ALLOW_COMMENTS, true);
 
        //序列化结果格式化，美化输出
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        //枚举输出成字符串
        //WRITE_ENUMS_USING_INDEX：输出索引
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        
        //空对象不要抛出异常：
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        
        //Date、Calendar等序列化为时间格式的字符串(如果不执行以下设置，就会序列化成时间戳格式)：
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        //反序列化时，遇到未知属性不要抛出异常：
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        
        //反序列化时，遇到忽略属性不要抛出异常：
        mapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        
        //反序列化时，空字符串对于的实例属性为null：
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        
        return mapper;
    }
    
}