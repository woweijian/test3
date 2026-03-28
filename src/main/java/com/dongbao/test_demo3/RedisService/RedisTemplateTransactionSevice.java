package com.dongbao.test_demo3.RedisService;

import org.apache.ibatis.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * @author : wangjunyue
 * date: 2025/11/20 19:46
 * Description: com.dongbao.test_demo3.RedisService
 * project: test_demo3
 */
@Service
public class RedisTemplateTransactionSevice {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;



    public List<Object> transaction(String... watchKeys) {
        return redisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                try {
                    // 监视键
                    if (watchKeys.length > 0) {
                        operations.watch(Arrays.asList(watchKeys));
                    }

                    // 开始事务
                    operations.multi();

                    // 事务操作
                    operations.opsForValue().set("test1", "a1");
                    operations.opsForValue().set("test2", "a2");
                    operations.opsForValue().set("test3", "a3");

                    // 执行事务
                    List<Object> execResult = operations.exec();

                    if (execResult == null) {
                        throw new RuntimeException("事务无法执行，监视的key被修改: " + Arrays.toString(watchKeys));
                    }

                    return execResult;

                } catch (Exception e) {
                    // ✅ 这里肯定在事务中，可以安全 discard
                    operations.discard();
                    throw new RuntimeException("执行Redis事务失败！", e);
                }
            }
        });
    }

    /**
     * 基本事务操作
     */
    public void basicTransaction() {
        List<Object> results = redisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi(); // 开始事务

                operations.opsForValue().set("tx_key1", "value1");
                operations.opsForValue().set("tx_key2", "value2");
                operations.opsForValue().increment("tx_counter");
                operations.opsForValue().get("tx_key1");

                return operations.exec(); // 执行事务
            }
        });

        System.out.println("事务执行结果: " + results);
    }

    /**
     * 事务 + 管道组合
     */
    public void transactionWithPipeline() {
        List<Object> results = redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi(); // 开启事务

                // 在事务中执行多个操作
                for (int i = 0; i < 10; i++) {
                    operations.opsForValue().set("batch_key_" + i, "batch_value_" + i);
                }

                operations.exec(); // 提交事务
                return null;
            }
        });

        System.out.println("事务管道结果: " + results);
    }

    /**
     * 监视键的事务（乐观锁）
     */
    public boolean watchTransaction(String key, String expectedValue, String newValue) {
        // 监视键
        // 检查当前值
        // 值已改变，取消监视
        // 开始事务
        // 执行事务
        // 取消事务
        return Boolean.TRUE.equals(redisTemplate.execute(new SessionCallback<Boolean>() {
            @Override
            public Boolean execute(RedisOperations operations) throws DataAccessException {
                operations.watch(key); // 监视键

                // 检查当前值
                String currentValue = (String) operations.opsForValue().get(key);
                if (!expectedValue.equals(currentValue)) {
                    operations.unwatch(); // 值已改变，取消监视
                    return false;
                }

                try {
                    operations.multi(); // 开始事务
                    operations.opsForValue().set(key, newValue);
                    operations.exec(); // 执行事务
                    return true;
                } catch (Exception e) {
                    operations.discard(); // 取消事务
                    return false;
                }
            }
        }));
    }
}
