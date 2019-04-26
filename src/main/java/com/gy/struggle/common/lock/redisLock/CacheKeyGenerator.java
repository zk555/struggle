package com.gy.struggle.common.lock.redisLock;

import com.gy.struggle.common.annotation.CacheLock;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * key生成器
 */
public interface CacheKeyGenerator {

    /**
     * 获取AOP参数,生成指定缓存Key
     * @param cacheLock
     * @return
     */
    String getLockKey(CacheLock cacheLock,final Object[] args,final Parameter[] parameters,Method method);

}
