package com.dongbao.test_demo3.Schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : wangjunyue
 * date: 2025/11/14 9:33
 * Description: com.dongbao.test_demo3.config
 * project: test_demo3
 */
@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    /**
     * 固定速率 - 每5秒执行一次（不管上次是否完成）
     */

    @Scheduled(fixedRate = 5000)
    public void taskWithFixedRate() {
        log.info("固定速率任务执行 - {}",sdf.format(new Date(System.currentTimeMillis())));
    }

    /**
     * 固定延迟 - 上次任务完成后，延迟3秒再执行
     */
    @Scheduled(fixedDelay = 5000)
    public void taskWithFixedDelay() {
        log.info("固定延迟任务执行 - {}", sdf.format(new Date(System.currentTimeMillis())));
        try {
            Thread.sleep(5000); // 模拟任务执行时间

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("固定延迟任务执行结束 - {}", sdf.format(new Date(System.currentTimeMillis())));
    }

    /**
     * 初始延迟 - 应用启动后延迟10秒开始，然后每5秒执行
     */
    @Scheduled(initialDelay = 10000, fixedRate = 5000)
    public void taskWithInitialDelay() {
        log.info("初始延迟任务执行 - {}", sdf.format(new Date(System.currentTimeMillis())));
    }

    @Async
    @Scheduled(initialDelay = 0,fixedRate = 1000)
    public void AsyncTask() {
        log.info("异步任务执行 - {}-{}", sdf.format(new Date(System.currentTimeMillis())), Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("异步任务执行结束 - {}-{}", sdf.format(new Date(System.currentTimeMillis())), Thread.currentThread().getName());
    }
}
