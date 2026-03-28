/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dongbao.test_demo3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "用户信息实体")
@TableName("emp_info")
public class EmpInfo {
    @Schema(description = "用户姓名", example = "张三", minLength = 2, maxLength = 20)
    private String name;
    @Schema(description = "用户工号", example = "013487", minimum = "0", maximum = "6")
    @TableField("emp_no")
    private String  empNo;
    @TableField("is_delete")
    private Integer  isDelete;

   @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
   @TableField("enable_date")
    private Date  enableDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @TableField("unable_date")
    private Date   unableDate;

    @TableId(type = IdType.INPUT)
    private Long id;



}
