package com.gy.struggle.common.lock.redisLock;

import com.gy.struggle.common.annotation.CacheLock;
import com.gy.struggle.common.annotation.CacheParam;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 通过接口注入的方式去写不同的生成规则; prefix:args[i]
 */
public class LockKeyGenerator  implements CacheKeyGenerator {
    @Override
    public String getLockKey(CacheLock cacheLock,final Object[] args,final Parameter[] parameters,Method method) {
        StringBuilder builder = new StringBuilder();
        //默认解析方法里面带 CacheParam 注解的属性,如果没有尝试着解析实体对象中的
        for (int i = 0; i < parameters.length; i++) {
            final CacheParam annotation = parameters[i].getAnnotation(CacheParam.class);
            if (annotation == null) {
                continue;
            }
            builder.append(cacheLock.delimiter()).append(args[i]);
        }if (StringUtils.isEmpty(builder.toString())) {
            final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                final Object object = args[i];
                final Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    final CacheParam annotation = field.getAnnotation(CacheParam.class);
                    if (annotation == null) {
                        continue;
                    }
                    field.setAccessible(true);
                    builder.append(cacheLock.delimiter()).append(ReflectionUtils.getField(field, object));
                }
            }
        }
        return cacheLock.prefix() + builder.toString();
    }
}
