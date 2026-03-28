package com.dongbao.test_demo3;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dongbao.test_demo3.Service.AttWorkHourDayService;
import com.dongbao.test_demo3.Service.EmpInfoService;
import com.dongbao.test_demo3.config.MybatisPlusConfig;
import com.dongbao.test_demo3.entity.AttWorkHourDay;
import com.dongbao.test_demo3.entity.EmpInfo;
import com.dongbao.test_demo3.mapper.EmpInfoMybatisPlusMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;


import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@MapperScan("com.dongbao.test_demo3.mapper")
@Import(MybatisPlusConfig.class)
@Slf4j
public class TestMybatisPlus {

    @Autowired
    private EmpInfoService empInfoService;

    @Autowired
    private EmpInfoMybatisPlusMapper empInfoMybatisPlusMapper;

    @Autowired
    private AttWorkHourDayService attWorkHourDayService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ApplicationContext  applicationContext;


    @Test
    public void testMybatisPlusSelectById() {
        System.out.println("----- Mybatis-Plus Select By ID Test -----");
        Long id = 1752930426629922818L; // Example ID
        EmpInfo empInfo = new EmpInfo();
        empInfo.setEmpNo("000000");
        empInfo.setName("测试用户");
        when(empInfoMybatisPlusMapper.selectById(id)).thenReturn(empInfo); // Mocking the database call
        EmpInfo re = empInfoService.getUserById(id);
        assertNotNull(empInfoService.getUserById(id));
       assertEquals("张三",re.getName());
        verify(empInfoMybatisPlusMapper,times(1)).selectById(id); // Verify that the method was called
    }


    @Test
    public void testMybatisPlusSelectByEmpno() {
        System.out.println("----- Mybatis-Plus Select By ID Test -----");
        String emp_no="013487"; // Example ID

        System.out.println(empInfoService.getUserByEmpno(emp_no));
    }


    @Test
    public void testMybatisPlusSelectByAttendanceDate() {


        String attendance_month = "2025-11";
        String attendance_date_begin = "2025-11-04";
        String attendance_date_end = "2025-11-05";



        // 2. 执行查询
        List<AttWorkHourDay> attWorkHourDays = attWorkHourDayService.selectByAttendanceDate(
                attendance_month, attendance_date_begin, attendance_date_end);

        // 3. 输出结果
        attWorkHourDays.forEach(System.out::println);



    }


    @Test
    public void testSelectByAttendanceDatePage(){
        int pageNum=1;
        int pageSize=100;

        String attendance_month = "2025-11";
        String attendance_date_begin = "2025-11-04";
        String attendance_date_end = "2025-11-05";

        Page<AttWorkHourDay> resultPage = attWorkHourDayService.selectByAttendanceDatePage(attendance_month, attendance_date_begin, attendance_date_end,pageNum,pageSize);
        // 分页信息
        System.out.println("当前页: " + resultPage.getCurrent());
        System.out.println("每页大小: " + resultPage.getSize());
        System.out.println("总记录数: " + resultPage.getTotal());
        System.out.println("总页数: " + resultPage.getPages());
        System.out.println("当前页数据: " + resultPage.getRecords().size());

        // 遍历当前页数据
        System.out.println("=== 当前页数据（共 " + resultPage.getRecords().size() + " 条）===");
        for (int i = 0; i < resultPage.getRecords().size(); i++) {
            AttWorkHourDay item = resultPage.getRecords().get(i);
            System.out.println((i + 1) + ". " + item);
        }

        // 模拟翻页
        while(resultPage.hasNext()&&pageNum<5){ // 最多翻5页，防止死循环{
            pageNum++;
            System.out.println("=== 模拟翻到第"+pageNum+"页 ===");

            Page<AttWorkHourDay> page2 = attWorkHourDayService.selectByAttendanceDatePage(
                    attendance_month, attendance_date_begin, attendance_date_end, pageNum, pageSize);
            System.out.println("第"+pageNum+"页数据条数: " + page2.getRecords().size());
        }
    }

    @Test
    public void testSimplePagination() {
        System.out.println("=== 最简单分页测试 ===");

        String month = "2025-11";
        String begin = "2025-11-04";
        String end = "2025-11-05";

        // 只测试分页，不混合其他查询
        Page<AttWorkHourDay> result = attWorkHourDayService.selectByAttendanceDatePage(
                month, begin, end, 1, 100);

        System.out.println("当前页数据条数: " + result.getRecords().size());
        System.out.println("期望: 100条");

        if (result.getRecords().size() == 100) {
            System.out.println("✅ 分页正常");
        } else {
            System.out.println("❌ 分页异常，返回了 " + result.getRecords().size() + " 条");
        }
    }


    @Test
    public void testSelectByName(){
        String name="王浚玥";
        String attendance_month = "2025-11";
        String attendance_date = "2025-11-04";
        AttWorkHourDay attWorkHourDay = attWorkHourDayService.selectOneByName(name, attendance_month, attendance_date);
        System.out.println(attWorkHourDay);
    }


    @Test
    public void testSelectByOriginId(){
        Long origin_id=1800874538998816770L;
        String attendance_month = "2025-11";
        String attendance_date = "2025-11-04";
        AttWorkHourDay attWorkHourDay = attWorkHourDayService.selectOneByOriginId(origin_id, attendance_month, attendance_date);
        System.out.println(attWorkHourDay);
    }


    @Test
    public void testUpdateByEmpNo(){
        String empNo="013487";
        empInfoService.updateEmpInfoByEmpNo(empNo);
    }









}
