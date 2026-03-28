package com.dongbao.test_demo3.Service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dongbao.test_demo3.CacheService.EmpInfoCacheService;
import com.dongbao.test_demo3.Service.EmpInfoService;
import com.dongbao.test_demo3.entity.EmpInfo;
import com.dongbao.test_demo3.mapper.EmpInfoMapper;
import com.dongbao.test_demo3.mapper.EmpInfoMybatisPlusMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class
EmpInfoServiceImpl implements EmpInfoService {

    @Autowired
    private EmpInfoMapper empInfoMapper;

    @Autowired
    private EmpInfoMybatisPlusMapper empInfoMybatisPlusMapper;

    @Autowired
    private EmpInfoCacheService empInfoCacheService;


    @Override
    @Transactional(
            propagation= Propagation.MANDATORY,
            isolation = Isolation.DEFAULT,
            rollbackFor = Exception.class,
            timeout=36000
    )
    public void updateEmpInfoByEmpNo(String empNo) {
        //1.先删缓存
        empInfoCacheService.deleteEmpInfoByEmpNo(empNo);
        log.info("删除缓存中的员工信息，员工编号: {}", empNo);
        //2.再更新数据库
          //a.先设置is_delete=1
        int i = DeleteEmpInfoByEmpNo(empNo);
        log.info("数据库删除员工信息，员工编号: {}", i);
          //b.再插入一条数据
           InsertEmpInfoByEmpNo(empNo);
           log.info("数据库插入员工信息，员工编号: {}", empNo);
        //3.再更新缓存
        empInfoCacheService.cacheEmpInfo(empNo, getUserByEmpno(empNo));
        log.info("更新缓存中的员工信息，员工编号: {}", empNo);
    }

    public void InsertEmpInfoByEmpNo(String empNo) {
        empInfoMapper.insertByEmpNo(empNo);
    }

    public int DeleteEmpInfoByEmpNo(String empNo) {
        int i = empInfoMybatisPlusMapper.fff();
        log.info("删除员工记录数: {}", i);
        return i;
    }

    @Override
    public EmpInfo getUserById1(String emp_no) {
        return empInfoMapper.getUserById(emp_no);
    }

    @Override
    public void updateUserByid(Long id) {
        empInfoMapper.updateUserByid(id);
    }

    @Override
    public EmpInfo getUserById(Long id) {

        return empInfoMybatisPlusMapper.selectById(id);
    }

    @Override
    public EmpInfo getUserByEmpno(String empNo) {

        //1. 先从缓存中获取
        Object cachedEmpInfo = empInfoCacheService.getCachedEmpInfo(empNo);
        log.info("从缓存中获取的员工信息: {}", cachedEmpInfo);
        if (cachedEmpInfo != null) {
            log.info("缓存命中，员工编号: {}", empNo);
            return (EmpInfo) cachedEmpInfo;
        }
        //2. 缓存中没有，从数据库中获取
        log.info("缓存未命中，查询数据库，员工编号: {}", empNo);
        LambdaQueryWrapper<EmpInfo> wrapper = Wrappers.lambdaQuery();
       wrapper.eq(EmpInfo::getEmpNo, empNo)
               .eq(EmpInfo::getIsDelete, 0)
               .apply("enable_date<=now() and unable_date>=now()");
        EmpInfo empInfo = empInfoMybatisPlusMapper.selectOne(wrapper);
        log.info("从数据库中查询到的员工信息: {}", empInfo);
        //3. 将查询结果缓存起来
        empInfoCacheService.cacheEmpInfo(empNo, empInfo);
        log.info(   "将员工信息缓存起来，员工编号: {}", empNo);
        return empInfo;

    }


}
