package com.example.demo.cti.shop.infrastructrue.util.operation;

/**
 * @author rain
 * @description:
 * @date 2023/3/18 3:06 下午
 */
public class Pow extends Operation{

    @Override
    public double compute(double one, double two) {
        return Math.pow(one, two);
    }

}
