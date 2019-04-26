package com.gy.struggle.common.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheLock {
    /**
     * redis 锁key前缀
     * @return
     */
    String prefix() default  "";

    /**
     * 过期秒数
     * @return
     */
    int expire() default 5;

    /**
     * 超时时间单位
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * <p>Key的分隔符（默认 :）</p>
     * <p>生成的Key：N:SO1008:500</p>
     * @return
     */
    String delimiter() default  ":";
}
