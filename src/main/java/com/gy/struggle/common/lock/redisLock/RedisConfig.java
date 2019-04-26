package com.gy.struggle.common.lock.redisLock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RedisConfig {
    @Bean
     public CacheKeyGenerator cacheKeyGenerator() {
         return new LockKeyGenerator();
     }
}
