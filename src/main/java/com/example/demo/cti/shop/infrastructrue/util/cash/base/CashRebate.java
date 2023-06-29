package com.example.demo.cti.shop.infrastructrue.util.cash.base;

/**
 * @author rain
 * @description:
 * @date 2023/3/18 3:18 下午
 */
public class CashRebate extends CashSuper {

    private double moneyRebate = 1d;

    public CashRebate(double moneyRebate) {
        this.moneyRebate = moneyRebate;
    }

    @Override
    public double acceptCash(double price, double num) {
        double result =  price * num * moneyRebate;
        return super.acceptCash(result,1);
    }

}
