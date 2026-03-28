package com.dongbao.test_demo3.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : wangjunyue
 * date: 2025/11/8 17:42
 * Description: com.dongbao.test_demo3.controller
 * project: test_demo3
 */
@RestController
public class LogTestController {

    private static final Logger log = LoggerFactory.getLogger(LogTestController.class);

    @GetMapping("/test-log")
    public String testLog() {
        System.out.println("✅ System.out 可以输出吗？");  // 先测试基础输出

        log.error("ERROR 级别日志测试");  // ERROR级别最高，应该能看到
        log.warn("WARN 级别日志测试");
        log.info("INFO 级别日志测试");
        log.debug("DEBUG 级别日志测试");

        return "检查控制台输出";
    }
}