package com.example.demo.cti;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.springframework.util.StringUtils;

import com.example.demo.cti.demo.Demo;

import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.ThreadUtil;

/**
 * @author rain
 * @description:
 * @date 2022/9/21 7:28 下午
 */
public class DemoTest {

    @Test
    public void fun() {
        String name = "name";
        StringUtils.hasText(name);
        List<String> stringList = new ArrayList<>();
        System.out.println("l" + stringList.size());
    }

    @Test
    public void fun1() {
        Demo demo = new Demo();
        List<String> aa = new ArrayList<>();
        aa.add("aa");
        aa.add("bb");
        demo.setTags(aa);
        demo.getTags().containsAll(null);
    }

    @Test
    public void fun2() {
        AtomicInteger i = new AtomicInteger(1);
        ConcurrencyTester concurrencyTester = ThreadUtil.concurrencyTest(10, () -> {
            i.set(i.get() + 10);
        });
        Console.log(concurrencyTester.getInterval());
        Console.log(i);
    }

}
