package com.dongbao.test_demo3.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("att_work_hour_day")
public class AttWorkHourDay {

    private String  isDelete;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Schema(description = "考勤日期", example = "2024-01-01")
    private Date  attendanceDate;
    private String attendanceMonth;
    private String empNo;
    private String empName;
}
