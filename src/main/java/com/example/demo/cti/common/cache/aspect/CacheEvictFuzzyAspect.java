package com.example.demo.cti.common.cache.aspect;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.example.demo.cti.common.cache.annotation.CacheEvictFuzzy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rain
 * @description: 利用AOP拦截CacheEvictFuzzy注解的方法实现key模糊匹配清除缓存
 * @date 2023/5/22 1:42 下午
 */
@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class CacheEvictFuzzyAspect {

    private final RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.example.demo.cti.common.cache.annotation.CacheEvictFuzzy)")
    public void fuzzyCacheEvict() {
    }

    @Around("fuzzyCacheEvict()")
    public Object doFuzzyCacheEvict(ProceedingJoinPoint joinPoint) throws Throwable {
        log.trace("----------进入-------");
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        CacheEvictFuzzy annotation = method.getAnnotation(CacheEvictFuzzy.class);
        String prefix = annotation.value();
        Object result = joinPoint.proceed(joinPoint.getArgs());
        deleteKey(joinPoint, prefix);
        log.trace("----------退出-------");
        return result;
    }

    private void deleteKey(ProceedingJoinPoint joinPoint, String prefix) {
        //从切点获取切点的所有参数
        Object[] allParams = joinPoint.getArgs();
        String objectId = allParams[0].toString();
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        Set<byte[]> keys = connection.keys((prefix + "*").getBytes());
        if (!CollectionUtils.isEmpty(keys)) {
            log.debug("----------所有的key '{}' objectId '{}' -------", keys, objectId);
            for (byte[] key : keys) {
                String deleteKey = new String(key, StandardCharsets.UTF_8);
                log.debug("----------执行的key '{}' -------", deleteKey);
                if (deleteKey.contains(objectId)) {
                    log.debug("---------删除key '{}' -------", deleteKey);
                    try {
                        redisTemplate.delete(deleteKey);
                    } catch (Exception e) {
                        log.warn("删除失败 key '{}' 请检查当前key是否存在", deleteKey);
                    }
                }
            }
        }
    }

}
