package com.dongbao.test_demo3.controller;

import com.dongbao.test_demo3.CacheService.EmpInfoCacheService;
import com.dongbao.test_demo3.Service.AttWorkHourDayService;
import com.dongbao.test_demo3.Service.EmpInfoService;
import com.dongbao.test_demo3.Tool.ApiResult;
import com.dongbao.test_demo3.Tool.ResultCode;
import com.dongbao.test_demo3.entity.AttWorkHourDay;
import com.dongbao.test_demo3.entity.AttWorkHourDayDto;
import com.dongbao.test_demo3.entity.EmpInfo;
import com.dongbao.test_demo3.entity.EmpInfoUto;
import com.dongbao.test_demo3.ulits.IsAcquire;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
public class HelloController {

    @Autowired

    private EmpInfoService empInfoService;

    @Autowired
    private AttWorkHourDayService attWorkHourDayService;

    @Autowired
    private IsAcquire isAcquire;


    @Tag(name = "登录", description = "登录欢迎")
    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        if(isAcquire.acquire(name,10,60)) {
            return "hello12344"+name;
        }else{
            return name+"被限流了";
        }
    }

    @GetMapping("/dhg1")
    @Tag(name = "人员信息", description = "根据工号姓名查找人员信息")
//     @RequestMapping (value="/dhg",method= RequestMethod.GET)
    public EmpInfo getUserInfo(@RequestParam String emp_no, @RequestParam(required = false) String name1) {
//         if(name1.equals("aaa")){
//             return null;
//         }
        EmpInfo empInfo = empInfoService.getUserById1(emp_no);
        log.info("根据工号{}查询到的人员信息为：{}", emp_no, empInfo);
        return empInfo;

    }

    @PutMapping("/dhg/{id}")
    @Tag(name = "修改人员信息", description = "根据id修改人员身高")
    public void updateUserByid(@PathVariable Long id) {
        System.out.println("debugger");
        empInfoService.updateUserByid(id);
    }

    @PostMapping("/dhg1")
    @Tag(name = "人员信息1", description = "根据工号姓名查找人员信息1")
//     @RequestMapping (value="/dhg",method= RequestMethod.GET)
    public ApiResult<EmpInfo> getUserInfo1(@RequestBody EmpInfoUto uto) {
        try {
            if (uto.getEmp_no() == null || uto.getEmp_no().equals("")) {
                return ApiResult.error(400, "工号不能为空");
            }

            EmpInfo empInfo1 = empInfoService.getUserById1(uto.getEmp_no());
            if (empInfo1 == null) {
                return ApiResult.error(404, "用户不存在");
            } else {

                return ApiResult.success(empInfo1, "查询成功");
            }
        } catch (Exception e) {
            System.out.println(e);
            return ApiResult.error(500, "服务器异常");
        }


    }

    @PostMapping("/dhg2")
    public ApiResult<List<AttWorkHourDay>> getAttWorkHourDay(@RequestBody AttWorkHourDayDto dto){
        try {
            if (dto.getAttendance_month() == null || dto.getAttendance_month().equals("")) {
                return ApiResult.error(400, "考勤月份不能为空");
            }
            if (dto.getAttendance_date() == null || dto.getAttendance_date().equals("")) {
                return ApiResult.error(400, "考勤开始日期不能为空");
            }
            log.info("开始：{}",System.currentTimeMillis());
            List<AttWorkHourDay> list = attWorkHourDayService.getAttWorkHourDay(dto.getAttendance_date(),dto.getAttendance_month());
            log.info("结束：{}",System.currentTimeMillis());
            if (list == null) {
                return ApiResult.error(404, "考勤信息不存在");
            } else {

                return ApiResult.success(list, "查询成功");
            }
        } catch (Exception e) {
            System.out.println(e);
            return ApiResult.error(500, "服务器异常");
        }

    }

    @PostMapping("/empinfo/{empNo}")
    public ApiResult updateEmpInfoByEmpNo(@PathVariable String emp_no){

        if(emp_no==null || emp_no.equals("")){
            log.info("工号不能为空");
            return  ApiResult.error(400,"工号不能为空");
        }else{

            return ApiResult.success(ResultCode.SUCCESS.getCode(),"更新成功");

        }

    }

}

