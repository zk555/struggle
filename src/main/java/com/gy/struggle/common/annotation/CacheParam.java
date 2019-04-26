package com.gy.struggle.common.annotation;

import java.lang.annotation.*;

/**
 * 锁的参数
 */

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheParam {

    /**
     * 字段名称
     * @return
     */
    String name() default "";
}
