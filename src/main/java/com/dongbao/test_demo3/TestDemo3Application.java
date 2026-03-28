package com.dongbao.test_demo3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.dongbao.test_demo3.mapper")
//@EnableScheduling
public class TestDemo3Application {

    public static void main(String[] args) {
        SpringApplication.run(TestDemo3Application.class, args);
    }

}
