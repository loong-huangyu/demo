package com.example.demo.cti.shop.infrastructrue.util.cash.base;

/**
 * @author rain
 * @description:
 * @date 2023/3/18 3:14 下午
 */
public class CashSuper implements Sale {

    protected Sale component;

    public void decorate(Sale component){
        this.component = component;
    }

    public double acceptCash(double price, double num) {
        double result = 0;
        if (this.component != null){
            result = this.component.acceptCash(price,num);
        }
        return result;
    }

}
