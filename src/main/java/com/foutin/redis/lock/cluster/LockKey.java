package com.foutin.redis.lock.cluster;

import java.lang.annotation.*;

/**
 * @author xingkai.fan
 * @description
 * @date 2019/7/26 18:27
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LockKey {
    String key() default "";
}
