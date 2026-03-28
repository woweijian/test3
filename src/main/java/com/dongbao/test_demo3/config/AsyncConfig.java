package com.dongbao.test_demo3.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author : wangjunyue
 * date: 2025/11/13 15:10
 * Description: com.dongbao.test_demo3.config
 * project: test_demo3
 */
@Configuration
@EnableAsync
@Slf4j
public class AsyncConfig {


    @Bean("taskExecutor" )
    public TaskExecutor taskExecutor(){
        log.info("异步任务执行器已启用");
        //ThreadPoolTaskExecutor 是 Spring 框架提供的一个线程池实现，用于管理和执行异步任务。是 Spring 对 ThreadPoolExecutor 的包装
        ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();
        threadPoolExecutor.setCorePoolSize(5);
        threadPoolExecutor.setMaxPoolSize(10);
        threadPoolExecutor.setQueueCapacity(25);
        threadPoolExecutor.setWaitForTasksToCompleteOnShutdown(true); // ✅ Spring 特有的优雅关闭
        threadPoolExecutor.setAwaitTerminationSeconds(30); // ✅ 等待任务完成的最大时间
        threadPoolExecutor.initialize();
        return threadPoolExecutor;
    }

}
