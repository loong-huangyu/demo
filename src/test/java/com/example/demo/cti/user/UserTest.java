package com.example.demo.cti.user;

import java.util.Optional;

import org.junit.Test;

import cn.hutool.core.lang.Console;

/**
 * @author rain
 * @description:
 * @date 2022/10/12 10:05 上午
 */
public class UserTest {

    @Test
    public void fun(){
        User user = new User("huang","yu","24");
        user.clearData();
        user.getEvent().forEach(abstractEvent -> {
            System.out.println(abstractEvent.toString());
        });
    }

    @Test
    public void fun1() {
        //User user = new User("huang","yu","24");
        User user = new User();
        user.setAge("12");
        String firstName = Optional.ofNullable(user.getName()).map(Name::getFirst).orElse(null);
        Console.log(firstName);
    }

}
