package com.example.demo.cti.common.log.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.example.demo.cti.common.log.annotation.NetLog;

import lombok.extern.slf4j.Slf4j;

/**
 * @author rain
 * @description: 获取登陆的IP 得到访问的网络供应商
 * Aspect 申明是一个切面与Component公用
 * @date 2023/3/9 7:46 下午
 */
@Slf4j
@Aspect
@Component
public class NetLogAspect {

    //指定范围
    @Pointcut("@annotation(com.example.demo.cti.demo.*)")
    public void pointcut()
    {

    }

    //环切
    @Around("@annotation(netLog)")
    public Object eventLogging(final ProceedingJoinPoint joinPoint, NetLog netLog) throws Throwable {
        HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipAddress = getIPAddress(request);
        log.debug("获取访问地址ip '{}' ", ipAddress);

        Object[] arguments = joinPoint.getArgs();
        //用来记录参数的值
        StringBuilder contentBuilder = new StringBuilder();
        //从切点获取切点的所有参数
        Object[] allParams = joinPoint.getArgs();

        for (Object param : allParams) {
            contentBuilder.append(JSON.toJSONString(param) + ",");
        }
        log.debug("参数 '{}' ", contentBuilder);
        Object object = joinPoint.proceed(arguments);
        return object;
    }

    public static String getIPAddress(HttpServletRequest request) {
        String ip = null;    //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {        //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {        //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {        //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {        //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }    //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }    //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
