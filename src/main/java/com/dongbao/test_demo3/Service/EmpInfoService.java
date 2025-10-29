package com.dongbao.test_demo3.Service;

import com.dongbao.test_demo3.entity.EmpInfo;

public interface EmpInfoService {

    EmpInfo getUserById(String emp_no) ;

    void updateUserByid(Long id);
}
