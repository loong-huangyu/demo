package com.example.demo.cti.common.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author rain
 * @description:
 * @date 2023/3/9 7:45 下午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface NetLog {

    String value() default "REQUEST";

    Class clazz() default NetLog.class;

    String message() default "";

}
