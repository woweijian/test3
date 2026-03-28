package com.dongbao.test_demo3;

import com.dongbao.test_demo3.Schedule.CronScheduledTasks;
import com.dongbao.test_demo3.Schedule.ScheduledTasks;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : wangjunyue
 * date: 2025/11/14 9:45
 * Description: com.dongbao.test_demo3
 * project: test_demo3
 */
@SpringBootTest
class ScheduledTasksTest {

    @Autowired
    private ScheduledTasks scheduledTasks;
    @Autowired
    private CronScheduledTasks cronScheduledTasks;

    @Test
    void testScheduledMethod() throws InterruptedException {
        // 直接调用定时任务方法进行测试
//        scheduledTasks.taskWithFixedRate();

        // 或者等待一段时间观察执行
        Thread.sleep(40000);
    }

    @Test
    void testCronScheduledMethod() throws InterruptedException {
        // 直接调用定时任务方法进行测试
//        cronScheduledTasks.everyMinute();

        // 或者等待一段时间观察执行
        Thread.sleep(20000);
    }

}