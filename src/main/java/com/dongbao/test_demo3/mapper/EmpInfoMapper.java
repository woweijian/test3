package com.dongbao.test_demo3.mapper;

import com.dongbao.test_demo3.entity.EmpInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmpInfoMapper {


    @Select("select emp_no,name,is_delete,enable_date,unable_date from emp_info where emp_no=#{empNo} and is_delete=0 and enable_date<=now() and unable_date>=now()")
    EmpInfo getUserById(String empNo);

    @Update("update emp_info set height=177.0000 where id=#{id} and is_delete=0")
    void updateUserByid(Long id);
}
