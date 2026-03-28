package com.dongbao.test_demo3.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author : wangjunyue
 * date: 2025/11/12 15:58
 * Description: com.dongbao.test_demo3.entity
 * project: test_demo3
 */

@Data
public class AttWorkHourDayDto {
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date attendance_date;
    private String attendance_month;


}
