package com.example.demo.cti.concurrency;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.alibaba.fastjson.JSON;

/**
 * @author rain
 * @description:
 * @date 2022/7/12 11:41 上午
 */
public class TestDay {

    @org.junit.Test
    public void test() {
        char test = 'a';
        switch (test) {
            case 'b': {
                System.out.println("b");
                break;
            }
            case 'c': {
                System.out.println("c");
                break;
            }
            case 'd': {
                System.out.println("d");
                break;
            }
            case 'a':
            case 'e': {
                System.out.println("e");
                break;
            }
        }
    }

    @org.junit.Test
    public void test2() {
        List<AA> booleanList = new ArrayList<>();
        booleanList.add(new AA(true, "1"));
        booleanList.add(new AA(false, "2"));
        booleanList.add(new AA(true, "3"));
        booleanList.add(new AA(false, "4"));
        booleanList.add(new AA(true, "5"));
        booleanList.add(new AA(true, "6"));
        booleanList.add(new AA(false, "7"));
        booleanList.add(new AA(false, "8"));

        booleanList = booleanList.
            stream().sorted(Comparator.comparing(AA::getA)).collect(Collectors.toList());

        System.out.println(booleanList.toString());
    }

    class AA {

        private Boolean a;

        private String b;

        public AA(Boolean a, String b) {
            this.a = a;
            this.b = b;
        }

        public Boolean getA() {
            return a;
        }

        public void setA(Boolean a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }

    }

}
