package com.example.demo.cti.shop.infrastructrue.util.cash.base;

/**
 * @author rain
 * @description:
 * @date 2023/3/18 3:20 下午
 */
public class CashReturn extends CashSuper {

    private double moneyCondition = 0d;

    private double moneyReturn = 0d;

    public CashReturn(double moneyCondition, double moneyReturn) {
        this.moneyCondition = moneyCondition;
        this.moneyReturn = moneyReturn;
    }

    @Override
    public double acceptCash(double price, double num) {
        double result = price * num;
        if (moneyCondition > 0 && result >= moneyCondition) {
            result = result - Math.floor(result / moneyCondition) * moneyReturn;
        }
        return super.acceptCash(result,1);
    }

}
