package com.example.demo.cti.shop.infrastructrue.util.cash.factory;

import com.example.demo.cti.shop.infrastructrue.util.cash.base.CashNormal;
import com.example.demo.cti.shop.infrastructrue.util.cash.base.CashRebate;
import com.example.demo.cti.shop.infrastructrue.util.cash.base.CashReturn;
import com.example.demo.cti.shop.infrastructrue.util.cash.base.Sale;

/**
 * @author rain
 * @description: 先打折再满减
 * @date 2023/3/18 6:08 下午
 */
public class CashRebateReturnFactory implements SaleFactory{

    private double moneyRebate = 1d;

    private double moneyCondition = 0d;

    private double moneyReturn = 0d;

    public CashRebateReturnFactory(double moneyRebate, double moneyCondition, double moneyReturn) {
        this.moneyRebate = moneyRebate;
        this.moneyCondition = moneyCondition;
        this.moneyReturn = moneyReturn;
    }

    @Override
    public Sale createSalesModel() {
        CashNormal cashNormal = new CashNormal();
        CashRebate cashRebate = new CashRebate(moneyRebate);
        CashReturn cashReturn = new CashReturn(moneyCondition, moneyReturn);
        cashRebate.decorate(cashNormal);
        cashReturn.decorate(cashRebate);
        return cashReturn;
    }

}
