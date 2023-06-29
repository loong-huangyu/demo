package com.example.demo.cti.shop.infrastructrue.util.cash.base;

/**
 * @author rain
 * @description:
 * @date 2023/3/18 3:17 下午
 */
public class CashNormal implements Sale {

    @Override
    public double acceptCash(double price, double num) {
        return price * num;
    }

}
