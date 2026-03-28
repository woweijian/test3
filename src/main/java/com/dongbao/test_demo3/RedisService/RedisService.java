package com.dongbao.test_demo3.RedisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author : wangjunyue
 * date: 2025/11/12 13:15
 * Description: com.dongbao.test_demo3.CacheService
 * project: test_demo3
 */
@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 设置字符串值
     */
    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置带过期时间的字符串值
     */
    public void setValueWithExpire(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取字符串值
     */
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除key
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 判断key是否存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置过期时间
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    // ========== Hash操作 ==========

    public void putHash(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Object getHash(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    // ========== List操作 ==========

    public void leftPush(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }
    public void leftPushWithExpire(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForList().leftPush(key, value);
        redisTemplate.expire(key, timeout, unit);
    }

    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    // 2. 获取范围元素
    public List<Object> getAll(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }
    // ========== Set操作 ==========

    public void addToSet(String key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    public Boolean isMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    public Object members(String key) {
        return redisTemplate.opsForSet().members(key);
    }



}
