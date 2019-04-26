package com.gy.struggle.common.lock.redisLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * 分布式锁 -- 》可以加在多服务器跑批
 */
@Component
public class RedisLock{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 加锁
     * @param key
     * @param value 当前时间+超时时间
     * @return
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 加锁
     * @param key
     * @param value 当前时间+超时时间
     * @return
     */
    public boolean lock(String key, String value) {
        //setnx
        if (stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        //currentValue=A 这两个线程的value都是B 其中一个线程拿到锁
        String currentValue = stringRedisTemplate.opsForValue().get(key);
        //如果锁过期
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            //获取上一个锁的时间
            String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value);
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     * @return
     */
    public void unlock(String key, String value) {
        String currentVaule = stringRedisTemplate.opsForValue().get(key);
        try {
            if (!StringUtils.isEmpty(currentVaule) && currentVaule.equals(value)) {
                stringRedisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
                logger.error("【redis分布式锁】解锁异常，{}" , e);
        }

    }
}
