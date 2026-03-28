package com.dongbao.test_demo3.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dongbao.test_demo3.CacheService.AttWorkHourDayCacheService;
import com.dongbao.test_demo3.Service.AttWorkHourDayService;
import com.dongbao.test_demo3.entity.AttWorkHourDay;
import com.dongbao.test_demo3.mapper.AttWorkHourDayPlusMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AttWorkHourDayServiceImpl extends ServiceImpl<AttWorkHourDayPlusMapper,AttWorkHourDay> implements AttWorkHourDayService {


    @Autowired
    private AttWorkHourDayPlusMapper attWorkHourDayPlusMapper;

    @Autowired
    private AttWorkHourDayCacheService attWorkHourDayCacheService;



    @Override
    public List<AttWorkHourDay> selectByAttendanceDate(String attendanceMonth, String attendanceDateBegin, String attendanceDateEnd) {
        LambdaQueryWrapper<AttWorkHourDay> wrapper = Wrappers.lambdaQuery();



        wrapper.eq(AttWorkHourDay::getIsDelete, 0)
                .eq(AttWorkHourDay::getAttendanceMonth, attendanceMonth)
                .apply("attendance_date>={0} and attendance_date<={1}", attendanceDateBegin, attendanceDateEnd);

        return attWorkHourDayPlusMapper.selectList(wrapper);

    }


    public Page<AttWorkHourDay> selectByAttendanceDatePage(String attendanceMonth, String attendanceDateBegin, String attendanceDateEnd, int pageNum, int pageSize) {
        LambdaQueryWrapper<AttWorkHourDay> wrapper = Wrappers.lambdaQuery();

        Page<AttWorkHourDay> page = new Page<>(pageNum,pageSize);

        wrapper.eq(AttWorkHourDay::getIsDelete, 0)
                .eq(AttWorkHourDay::getAttendanceMonth, attendanceMonth)
                .apply("attendance_date>={0} and attendance_date<={1}", attendanceDateBegin, attendanceDateEnd);

        return this.page(page,wrapper);
//        baseMapper.selectPage(page,wrapper)


    }

    @Override
    public AttWorkHourDay selectOneByName(String name, String attendanceMonth, String attendanceDate) {
        return attWorkHourDayPlusMapper.selectOneByName(name, attendanceMonth, attendanceDate);
    }

    @Override
    public AttWorkHourDay selectOneByOriginId(Long originId, String attendanceMonth, String attendanceDate) {
        return attWorkHourDayPlusMapper.selectOneByOriginId(originId, attendanceMonth, attendanceDate);
    }

    @Async("taskExecutor")
    @Override
    public List<AttWorkHourDay> getAttWorkHourDay(Date attendanceDate, String attendanceMonth) {
        Object cachedAttWorkHourDay = attWorkHourDayCacheService.getCachedAttWorkHourDay("abc");
        if(cachedAttWorkHourDay!=null){
            log.info("从缓存中获取考勤日工时数据");
            System.out.println(22);
            return (List<AttWorkHourDay>) cachedAttWorkHourDay;
        }
        // 从数据库中获取
        log.info("从数据库中获取考勤日工时数据");
//        LambdaQueryWrapper<AttWorkHourDay> wrapper = Wrappers.lambdaQuery();
//        wrapper.eq(AttWorkHourDay::getIsDelete, 0)
//                .eq(AttWorkHourDay::getAttendanceMonth, attendanceMonth);
//                .eq(AttWorkHourDay::getAttendanceDate, attendanceDate);
//        List<AttWorkHourDay> attWorkHourDays = attWorkHourDayPlusMapper.selectList(wrapper);
        List<AttWorkHourDay> attWorkHourDays = attWorkHourDayPlusMapper.selectList1();
        // 缓存结果
        log.info("将考勤日工时数据缓存起来");
        attWorkHourDayCacheService.CacheAttWorkHourDay("abc", attWorkHourDays);
        log.info("考勤日工时数据缓存完成");
        System.out.println("11");
        return attWorkHourDays;

    }


}
