package com.dongbao.test_demo3.ulits;

import com.dongbao.test_demo3.RedisService.RedisTemplateLuaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author : wangjunyue
 * date: 2025/11/20 8:44
 * Description: com.dongbao.test_demo3.ulits
 * project: test_demo3
 */
@Component
public class IsAcquire {

    @Autowired
     private RedisTemplate<String, String> redisTemplate;

    //RedisLua  脚本执行类
    @Autowired
    private RedisTemplateLuaService redisTemplateLuaService;

    private  DefaultRedisScript<Long>   defaultRedisScript;

    public boolean acquire(String name, int limit, int expire) {
        //方法一  通过加载lua脚本文件
        defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Long.class);
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/isAcquire.lua")));
        //singletonList  用于单个key的情况
        Long execute = redisTemplate.execute(defaultRedisScript, Collections.singletonList(name), String.valueOf(limit), String.valueOf(expire));
        //Arrays.asList()  用于多个key的情况
//        Long execute = redisTemplate.execute(defaultRedisScript, Arrays.asList(name,"a","b"), String.valueOf(limit), String.valueOf(expire));
        if(execute ==0) {
            return false;
        }else {
            return true;
        }

        //方法二   直接用写死的脚本操作  适合脚本简单的
//        redisTemplateLuaService.rateLimit(name,limit,expire);
    }
}
