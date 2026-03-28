package com.dongbao.test_demo3;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dongbao.test_demo3.CacheService.EmpInfoCacheService;
import com.dongbao.test_demo3.RedisService.RedisService;
import com.dongbao.test_demo3.RedisService.RedisTemplatePipelineService;
import com.dongbao.test_demo3.RedisService.RedisTemplateTransactionSevice;
import com.dongbao.test_demo3.Service.AttWorkHourDayService;
import com.dongbao.test_demo3.Service.EmpInfoService;
import com.dongbao.test_demo3.entity.AttWorkHourDay;
import com.dongbao.test_demo3.entity.EmpInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SpringBootTest
class RedisConnectionTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private EmpInfoService empInfoService;

    @Autowired
    private AttWorkHourDayService attWorkHourDayService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private EmpInfoCacheService     empInfoCacheService;

    @Autowired
    private RedisTemplatePipelineService redisTemplatePipelineService;

    @Autowired
    private RedisTemplateTransactionSevice redisTemplateTransactionSevice;




    @Test
    void testBasicConnection() {
        System.out.println("=== Redis基础连接测试 ===");

        try {
            // 测试设置值
            redisTemplate.opsForValue().set("test_key", "Hello Redis!");

            // 测试获取值
            String value = redisTemplate.opsForValue().get("test_key");

            System.out.println("✅ Redis连接成功！");
            System.out.println("测试值: " + value);

            // 清理测试数据
            redisTemplate.delete("test_key");

        } catch (Exception e) {
            System.out.println("❌ Redis连接失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    void testString(){
        redisTemplate.opsForValue().set("name","zhangsan");
        String name = redisTemplate.opsForValue().get("name");
        System.out.println("name="+name);

    }

    @Test
    void testGetUser(){
        EmpInfo empinfo = empInfoService.getUserByEmpno("013487");
        redisTemplate.opsForHash().put("empinfo","emp_no",empinfo.getEmpNo());
        redisTemplate.opsForHash().put("empinfo","name",empinfo.getName());
        Object emp_no = redisTemplate.opsForHash().get("empinfo", "name");
        List<Object> objects = redisTemplate.opsForHash().multiGet("empinfo", Arrays.asList("emp_no", "name"));
        System.out.println("emp_no="+objects.get(0)+",name="+objects.get(1));

    }


    @Test
    void testGetSet(){
        String attendance_month = "2025-11";
        String attendance_date_begin = "2025-11-04";
        String attendance_date_end = "2025-11-05";
        Page<AttWorkHourDay> resultPage = attWorkHourDayService.selectByAttendanceDatePage(attendance_month, attendance_date_begin, attendance_date_end,1,10);
        redisService.addToSet("attendance_days", resultPage.getRecords());
        Set<Object> attendanceDays = (Set<Object>) redisService.members("attendance_days");
        for (Object attendanceDay : attendanceDays) {

           List<AttWorkHourDay> list=(List<AttWorkHourDay>) attendanceDay;
            for (int i = 0; i <list.size() ; i++) {
                System.out.println(list.get(i));
            }
        }
    }


    @Test
    void testCache(){
        String emp_no="013487"; // Example ID
        EmpInfo empInfo = empInfoService.getUserByEmpno(emp_no);


    }

    @Test
    void testSync(){
        LocalDate localDate = LocalDate.parse("2025-11-12");
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
      attWorkHourDayService.getAttWorkHourDay(date, "2025-11");

        System.out.println("异步方法已调用，主线程继续执行");

        // ⭐️ 重要：等待异步方法执行完成
        try {
            Thread.sleep(5000); // 等待5秒
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("异步方法调用完成");
    }


    @Test
    void testPipeline(){


        List<Object> objects = redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) {
                // String 操作
                operations.opsForValue().set("user:1:name", "张三");
                operations.opsForValue().set("user:1:age", "25");

                // Hash 操作
                operations.opsForHash().put("user:2:info", "email", "zhangsan@example.com");
                operations.opsForHash().put("user:2:info", "phone", "13800138000");

                // Set 操作

                operations.opsForSet().add("user:2:tags", "VIP", "NEW_USER");

                // List 操作
                operations.opsForList().rightPush("user:1:logs", "login_at_" + System.currentTimeMillis());

                // 获取结果

                operations.opsForValue().get("user:1:name");

                operations.opsForHash().get("user:2:info", "email");
                operations.opsForSet().members("user:2:tags");

                return null;
            }
        });
        objects.forEach(obj -> System.out.println("Pipeline Result: " + obj));

    }


    @Test
    void testTransaction(){
        List<Object> objects = redisTemplateTransactionSevice.transaction("test1","test2");
        objects.forEach(obj -> System.out.println("Transaction Result: " + obj));
    }



}