package com.dongbao.test_demo3;


import com.dongbao.test_demo3.entity.EmpInfo;
import com.dongbao.test_demo3.mapper.EmpInfoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
public class DebugApplication {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private EmpInfoMapper empInfoMapper;

    @Test
    public void testDataSource() throws Exception {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());

    }

    @Test
    public void update(){
        EmpInfo empInfo = new EmpInfo();
        empInfo.setName("王浚玥");
        System.out.println("before "+empInfo.getName()+empInfo.getEmpNo());
        empInfoMapper.updateUserByName(empInfo.getName());
        System.out.println("after "+empInfo.getName()+empInfo.getEmpNo());
    }




}
