package com.gy.struggle.common.lock.redisLock;

import com.gy.struggle.common.annotation.CacheLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 保证分布式情况下同一时刻只能有一个服务器的Controller执行
 */
@Aspect
@Configuration
public class LockMethodInterceptor {

    private final StringRedisTemplate lockRedisTemplate;

    private final CacheKeyGenerator cacheKeyGenerator;

    @Autowired
     public LockMethodInterceptor(StringRedisTemplate lockRedisTemplate, CacheKeyGenerator cacheKeyGenerator) {
                 this.lockRedisTemplate = lockRedisTemplate;
                 this.cacheKeyGenerator = cacheKeyGenerator;
      }

    @Around("execution(public * *(..)) && @annotation(com.gy.struggle.common.annotation.CacheLock)")
    public Object interceptor(ProceedingJoinPoint pjp) {
         MethodSignature signature = (MethodSignature) pjp.getSignature();
         Method method = signature.getMethod();
         CacheLock lock = method.getAnnotation(CacheLock.class);
         //如果前置为空直接抛出异常
         if (StringUtils.isEmpty(lock.prefix())) {
                 throw new RuntimeException("lock key can't be null...");
          }
         final String lockKey = cacheKeyGenerator.getLockKey(lock ,pjp.getArgs(),method.getParameters(),method);
         try {
                 //key不存在才能设置成功
                 final Boolean success = lockRedisTemplate.opsForValue().setIfAbsent(lockKey, "加锁");
                 if (success) {
                         lockRedisTemplate.expire(lockKey, lock.expire(), lock.timeUnit());
                     } else {
                         //按理来说 我们应该抛出一个自定义的 CacheLockException 异常;
                         throw new RuntimeException("请勿重复请求");
                     }
                 try {
                         return pjp.proceed();
                     } catch (Throwable throwable) {
                         throw new RuntimeException("系统异常");
                     }
             } finally {
                 //如果演示的话需要注释该代码;实际应该放开
                  lockRedisTemplate.delete(lockKey);
         }
    }
}
