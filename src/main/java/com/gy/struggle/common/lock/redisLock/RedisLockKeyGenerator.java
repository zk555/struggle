package com.gy.struggle.common.lock.redisLock;

import com.gy.struggle.common.annotation.RedisLock;
import com.gy.struggle.common.annotation.RedisParam;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 通过接口注入的方式去写不同的生成规则; prefix:args[i]
 */
public class RedisLockKeyGenerator implements RedisKeyGenerator {
    @Override
    public String getLockKey(final RedisLock redisLock, final Object[] args, final Parameter[] parameters, Method method) {
        StringBuilder builder = new StringBuilder();
        //默认解析方法里面带 RedisParam 注解的属性,如果没有尝试着解析实体对象中的
        for (int i = 0; i < parameters.length; i++) {
            final RedisParam annotation = parameters[i].getAnnotation(RedisParam.class);
            if (annotation == null) {
                continue;
            }
            builder.append(redisLock.delimiter()).append(args[i]);
        }if (StringUtils.isEmpty(builder.toString())) {
            final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                final Object object = args[i];
                final Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    final RedisParam annotation = field.getAnnotation(RedisParam.class);
                    if (annotation == null) {
                        continue;
                    }
                    field.setAccessible(true);
                    builder.append(redisLock.delimiter()).append(ReflectionUtils.getField(field, object));
                }
            }
        }
        return redisLock.prefix() + builder.toString();
    }
}
