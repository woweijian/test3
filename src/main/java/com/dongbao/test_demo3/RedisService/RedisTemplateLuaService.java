package com.dongbao.test_demo3.RedisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author : wangjunyue
 * date: 2025/11/20 10:52
 * Description: com.dongbao.test_demo3.CacheService
 * project: test_demo3
 */
@Service
public class RedisTemplateLuaService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 执行简单的 Lua 脚本
     */
    public Object executeSimpleLua() {
        String luaScript =
                "local key = KEYS[1] " +
                        "local value = ARGV[1] " +
                        "redis.call('SET', key, value) " +
                        "return redis.call('GET', key)";

        DefaultRedisScript<String> script = new DefaultRedisScript<>();
        script.setScriptText(luaScript);
        script.setResultType(String.class);

        return redisTemplate.execute(
                script,
                Collections.singletonList("lua_key"),
                "lua_value"
        );

    }

    /**
     * 限流 Lua 脚本（60秒10次）
     */
    public boolean rateLimit(String key, int maxRequests, int windowSeconds) {
        String luaScript =
                "local key = KEYS[1] " +
                        "local max = tonumber(ARGV[1]) " +
                        "local window = tonumber(ARGV[2]) " +
                        "local current = redis.call('INCR', key) " +
                        "if current == 1 then " +
                        "    redis.call('EXPIRE', key, window) " +
                        "end " +
                        "if current > max then " +
                        "    return 0 " +
                        "else " +
                        "    return 1 " +
                        "end";

        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(luaScript);
        script.setResultType(Long.class);

        Long result = redisTemplate.execute(
                script,
                Collections.singletonList("rate_limit:" + key),
                maxRequests,
                windowSeconds
        );

        return result != null && result == 1;
    }
}