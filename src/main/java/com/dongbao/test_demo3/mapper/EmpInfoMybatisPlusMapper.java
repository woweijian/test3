package com.dongbao.test_demo3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dongbao.test_demo3.entity.EmpInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EmpInfoMybatisPlusMapper extends BaseMapper<EmpInfo> {

   @Select("select * from emp_info where id=#{id}")
    EmpInfo selectById1(@Param("id") long id);

    @Update("UPDATE emp_info SET is_delete = 1 WHERE id= 1839563230621540353 and enable_date<=now() and unable_date>=now() and is_delete=0")
    int fff();
}
