package com.lrc.ocr.domain.service.manage;

import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisLimiterManager {
    @Resource
    private RedissonClient redissonClient;

    /**
     * 执行速率限制的方法。
     * @param key 用于限流的键
     * @return 如果在限流规则允许的范围内，返回true；否则返回false
     */
    public boolean doRateLimit(String key){
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        // 获取限流器实例，用于限制指定键的请求频率
        // 设置限流规则：每秒最多2个请求，如果超过这个频率，则拒绝服务
        // 其中，2表示每秒允许的最大请求数量，1表示在超过最大请求数量后，允许的请求数量
        // RateIntervalUnit.SECONDS表示统计时间的单位是秒
        rateLimiter.trySetRate(RateType.OVERALL,2,1, RateIntervalUnit.SECONDS);
        // 尝试设置限流规则，如果规则冲突，则不会成功
        // 尝试获取许可，如果获取成功，则表示请求被允许，返回true
        // 如果在指定时间内无法获取许可，则返回false，表示请求被限流
        return rateLimiter.tryAcquire(1);
    }

}