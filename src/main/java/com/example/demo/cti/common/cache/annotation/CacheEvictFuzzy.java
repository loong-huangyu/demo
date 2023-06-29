package com.example.demo.cti.common.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author rain
 * @description: 模糊匹配清除缓存
 * 使用此注解的方法第一个参数值为包含的值
 * @date 2023/5/22 1:40 下午
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CacheEvictFuzzy {

    /**
     * 需要校验的包名 即服务名+缓存名称
     */
    String value();

}
