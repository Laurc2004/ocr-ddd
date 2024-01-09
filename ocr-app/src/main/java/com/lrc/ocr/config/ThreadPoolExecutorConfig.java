package com.lrc.ocr.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolExecutorConfig {

    /**
     * 创建线程池的Bean
     * @return 返回一个配置好的线程池执行器
     */
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        int corePoolSize = 6; // 核心线程数，稍高于CPU核心数
        int maximumPoolSize = 12; // 最大线程数，CPU核心数的3倍
        int keepAliveTime = 30; // 线程空闲时间，设置为30秒
        TimeUnit unit = TimeUnit.SECONDS;
        int queueCapacity = 4; // 工作队列的容量，设置为4个任务
        ThreadFactory threadFactory = new ThreadFactory() {
            private int count = 1;

            @Override
            public Thread newThread(@NotNull Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("线程" + count);
                count++;
                return thread;
            }
        };

        // 创建并返回线程池执行器
        // 参数解释：
        // corePoolSize: 核心线程数，即始终存在的线程数（除非设置了allowCoreThreadTimeOut）
        // maximumPoolSize: 最大线程数，即当工作队列满时最大的线程数
        // keepAliveTime: 线程空闲时间，超过这个时间的空闲线程将被终止
        // unit: keepAliveTime的时间单位
        // workQueue: 用于存放任务的阻塞队列
        // threadFactory: 线程工厂，用于创建线程
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                new ArrayBlockingQueue<>(queueCapacity), threadFactory);
    }
}
