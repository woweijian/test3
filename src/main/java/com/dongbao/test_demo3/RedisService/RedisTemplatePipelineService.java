package com.dongbao.test_demo3.RedisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : wangjunyue
 * date: 2025/11/20 15:14
 * Description: com.dongbao.test_demo3.RedisService
 * project: test_demo3
 */
@Service
public class RedisTemplatePipelineService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 基本的管道操作
     */
    public List<Object> pipelineBasic() {
        return redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) {
                for (int i = 0; i < 100; i++) {
                    operations.opsForValue().set("pipe_key_" + i, "value_" + i);
                    operations.opsForValue().get("pipe_key_" + i);
                }
                return null;
            }
        });
    }

    /**
     * 混合数据类型的管道操作
     */
    public List<Object> pipelineMixedOperations() {
        return redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) {
                // String 操作
                operations.opsForValue().set("user:1:name", "张三");
                operations.opsForValue().set("user:1:age", "25");

                // Hash 操作
                operations.opsForHash().put("user:1:info", "email", "zhangsan@example.com");
                operations.opsForHash().put("user:1:info", "phone", "13800138000");

                // Set 操作
                operations.opsForSet().add("user:1:tags", "VIP", "NEW_USER");

                // List 操作
                operations.opsForList().rightPush("user:1:logs", "login_at_" + System.currentTimeMillis());

                // 获取结果

                operations.opsForValue().get("user:1:name");

             operations.opsForHash().get("user:1:info", "email");
                operations.opsForSet().members("user:1:tags");

                return null;
            }
        });
    }

    /**
     * 带返回值的管道操作
     */
    public void pipelineWithResults() {
        List<Object> results = redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) {
                // 设置值
                operations.opsForValue().set("counter", "100");

                // 增加计数器
                operations.opsForValue().increment("counter");
                operations.opsForValue().increment("counter");
                operations.opsForValue().increment("counter");

                // 获取最终值
                operations.opsForValue().get("counter");

                return null;
            }
        });

        // 处理管道结果
        System.out.println("管道操作结果:");
        for (int i = 0; i < results.size(); i++) {
            System.out.println("操作 " + i + ": " + results.get(i));
        }
        // 输出: 操作 0: OK, 操作 1: 101, 操作 2: 102, 操作 3: 103, 操作 4: 103
    }

}
