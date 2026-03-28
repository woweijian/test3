package com.dongbao.test_demo3.Service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dongbao.test_demo3.entity.AttWorkHourDay;

import java.util.Date;
import java.util.List;


public interface AttWorkHourDayService extends IService<AttWorkHourDay> {


    List<AttWorkHourDay> selectByAttendanceDate(String attendanceMonth, String attendanceDateBegin, String attendanceDateEnd);

    Page<AttWorkHourDay> selectByAttendanceDatePage(String attendanceMonth, String attendanceDateBegin, String attendanceDateEnd, int pageNum, int pageSize);

    AttWorkHourDay selectOneByName(String name, String attendanceMonth, String attendanceDate);

    AttWorkHourDay selectOneByOriginId(Long originId, String attendanceMonth, String attendanceDate);

    List<AttWorkHourDay> getAttWorkHourDay(Date attendanceDate, String attendanceMonth);


}
