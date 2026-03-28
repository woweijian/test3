package com.dongbao.test_demo3.Schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author : wangjunyue
 * date: 2025/11/14 10:49
 * Description: com.dongbao.test_demo3.Schedule
 * project: test_demo3
 */
@Component
@Slf4j
public class CronScheduledTasks {


    /**
     * 每分钟执行一次
     */
//    @Scheduled(cron = "0 * * * * ?")
//    public void everyMinute() {
//        log.info("每分钟任务执行 - {}", new Date());
//    }
//
//    /**
//     * 每5分钟执行一次
//     */
//    @Scheduled(cron = "0 */5 * * * ?")
//    public void everyFiveMinutes() {
//        log.info("每5分钟任务执行 - {}", new Date());
//    }
//
//    /**
//     * 每天上午10:15执行
//     */
//    @Scheduled(cron = "0 53 10 * * ?")
//    public void everyDayAt1015() {
//        log.info("每天10:15执行 - {}", new Date());
//    }
//
//    /**
//     * 工作日（周一到周五）上午9点执行
//     */
//    @Scheduled(cron = "0 0 9 * * MON-FRI")
//    public void weekdaysAt9() {
//        log.info("工作日9点执行 - {}", new Date());
//    }


}
