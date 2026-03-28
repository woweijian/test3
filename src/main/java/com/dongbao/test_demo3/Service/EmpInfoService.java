package com.dongbao.test_demo3.Service;

import com.dongbao.test_demo3.entity.AttWorkHourDay;
import com.dongbao.test_demo3.entity.EmpInfo;

import java.util.Date;
import java.util.List;

public interface EmpInfoService {

     void updateEmpInfoByEmpNo(String empNo);


    EmpInfo getUserById1(String emp_no) ;

    void updateUserByid(Long id);
    EmpInfo getUserById(Long id) ;

    EmpInfo getUserByEmpno(String empNo);


}
