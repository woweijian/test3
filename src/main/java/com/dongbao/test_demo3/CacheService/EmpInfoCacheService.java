package com.dongbao.test_demo3.CacheService;

import com.dongbao.test_demo3.RedisService.RedisService;
import com.dongbao.test_demo3.entity.EmpInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;



/**
 * @author : wangjunyue
 * date: 2025/11/12 14:17
 * Description: com.dongbao.test_demo3.CacheService
 * project: test_demo3
 */
@Service
@Slf4j
public class EmpInfoCacheService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private ObjectMapper objectMapper;


    public void cacheEmpInfo(String empNo, Object empInfo){
        String key = "empInfo:" + empNo;
        redisService.setValueWithExpire(key, empInfo, 60, java.util.concurrent.TimeUnit.MINUTES);
    }

    public Object getCachedEmpInfo(String empNo){
        String key = "empInfo:" + empNo;
        try {
            Object obj = redisService.getValue(key);
            if (obj == null) {
                return null;
            }

            if (obj instanceof EmpInfo) {
                return (EmpInfo) obj;
            } else if (obj instanceof LinkedHashMap) {
                // 转换 LinkedHashMap 到 EmpInfo
                return objectMapper.convertValue(obj, EmpInfo.class);
            } else {
                log.warn("未知的缓存数据类型: {}", obj.getClass().getName());
                return null;
            }
        } catch (Exception e) {
            log.error("获取缓存员工信息失败: ", e);
            return null;
        }

    }

    public void deleteEmpInfoByEmpNo(String empNo) {
        String key = "empInfo:" + empNo;
        redisService.delete(key);
        log.error("已删除缓存员工信息，key: {}", key);
    }
}
