package com.lrc.ocr.config;// 导入slf4j日志框架，用于记录日志
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类
 * 主要用于配置RedisTemplate，为Spring框架中的Redis操作提供配置
 */
@Configuration
@Slf4j
public class RedisConfiguration {


    /**
     * 创建RedisTemplate的Bean
     * @param redisConnectionFactory Redis连接工厂，用于生成Redis连接
     * @return 返回配置好的RedisTemplate对象
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        // 设置RedisTemplate的连接工厂，使其能够通过工厂获取Redis连接
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置Redis key的序列化器，这里使用StringRedisSerializer来进行序列化
        // 这是为了保证Redis中的key是String类型，便于管理和操作
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 记录日志，表示RedisTemplate设置成功
        log.info("redis设置成功： {}",redisTemplate);
        // 返回配置好的RedisTemplate对象，以便其他Bean可以进行注入和使用
        return redisTemplate;
    }
}
