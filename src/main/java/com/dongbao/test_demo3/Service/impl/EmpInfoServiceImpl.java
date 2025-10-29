package com.dongbao.test_demo3.Service.impl;


import com.dongbao.test_demo3.Service.EmpInfoService;
import com.dongbao.test_demo3.entity.EmpInfo;
import com.dongbao.test_demo3.mapper.EmpInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpInfoServiceImpl implements EmpInfoService {

    @Autowired
    private EmpInfoMapper empInfoMapper;
    @Override
    public EmpInfo getUserById(String emp_no) {
        return empInfoMapper.getUserById(emp_no);
    }

    @Override
    public void updateUserByid(Long id) {
        empInfoMapper.updateUserByid(id);
    }
}
