package com.dongbao.test_demo3.CacheService;

import com.dongbao.test_demo3.RedisService.RedisService;
import com.dongbao.test_demo3.entity.AttWorkHourDay;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author : wangjunyue
 * date: 2025/11/12 15:55
 * Description: com.dongbao.test_demo3.CacheService
 * project: test_demo3
 */
@Service
@Slf4j
public class AttWorkHourDayCacheService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private ObjectMapper objectMapper;

    public void CacheAttWorkHourDay(String key,Object value){
        redisService.leftPushWithExpire(key,value, 60, java.util.concurrent.TimeUnit.MINUTES);
    }

    public Object getCachedAttWorkHourDay(String key){
        try {
            Object obj = redisService.getAll(key);

            if (obj instanceof ArrayList) {
                return (List<AttWorkHourDay>) obj;
            } else if (obj instanceof LinkedHashMap) {
                // 转换 LinkedHashMap 到 EmpInfo
                return objectMapper.convertValue(obj, AttWorkHourDay.class);
            } else {
                log.warn("未知的缓存数据类型: {}", obj.getClass().getName());
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }

}
