package com.dongbao.test_demo3;

import com.dongbao.test_demo3.Service.EmpInfoService;
import com.dongbao.test_demo3.entity.EmpInfo;
import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestDemo3ApplicationTests {

    @Autowired
    private EmpInfoService empInfoService;
    @Test
    void contextLoads() {
        EmpInfo empInfo1 = empInfoService.getUserById1("013487");
        System.out.println(empInfo1);
    }

    @Test
    void test1() {
        EmpInfo empInfo1 = empInfoService.getUserById1("013486");
        System.out.println(empInfo1);
    }

}
