package com.gy.struggle.common.lock.redisLock;

import com.gy.struggle.common.annotation.RedisLock;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * key生成器
 */
public interface RedisKeyGenerator {

    /**
     * 获取AOP参数,生成指定缓存Key
     * @param cacheLock
     * @return
     */
    String getLockKey(RedisLock redisLock, final Object[] args, final Parameter[] parameters, Method method);

}
