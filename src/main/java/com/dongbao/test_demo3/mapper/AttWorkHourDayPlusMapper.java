package com.dongbao.test_demo3.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dongbao.test_demo3.entity.AttWorkHourDay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AttWorkHourDayPlusMapper extends BaseMapper<AttWorkHourDay> {


    @Select("select * from att_work_hour_day where is_delete=0 and emp_name=#{name} and attendance_month=#{attendanceMonth} and attendance_date=#{attendanceDate} ")
    AttWorkHourDay selectOneByName(@Param("name")String name, @Param("attendanceMonth")String attendanceMonth, @Param("attendanceDate")String attendanceDate);

    AttWorkHourDay selectOneByOriginId(@Param("originId")Long originId, @Param("attendanceMonth")String attendanceMonth, @Param("attendanceDate")String attendanceDate);

    @Select("select * from att_work_hour_day where is_delete=0 and attendance_month<='2025-11'  and attendance_month>='2025-10'")
    List<AttWorkHourDay> selectList1();
}

