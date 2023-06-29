package com.example.demo.cti.shop.infrastructrue.util.cash.factory;

import com.example.demo.cti.shop.infrastructrue.util.cash.base.CashNormal;
import com.example.demo.cti.shop.infrastructrue.util.cash.base.CashRebate;
import com.example.demo.cti.shop.infrastructrue.util.cash.base.CashReturn;
import com.example.demo.cti.shop.infrastructrue.util.cash.base.Sale;

/**
 * @author rain
 * @description: 先满减再打折
 * @date 2023/3/18 6:08 下午
 */
public class CashReturnRebateFactory implements SaleFactory {

    private double moneyRebate = 1d;

    private double moneyCondition = 0d;

    private double moneyReturn = 0d;

    public CashReturnRebateFactory(double moneyRebate, double moneyCondition, double moneyReturn) {
        this.moneyRebate = moneyRebate;
        this.moneyCondition = moneyCondition;
        this.moneyReturn = moneyReturn;
    }

    @Override
    public Sale createSalesModel() {
        CashNormal cashNormal = new CashNormal();
        CashReturn cashReturn = new CashReturn(moneyCondition, moneyReturn);
        CashRebate cashRebate = new CashRebate(moneyRebate);
        cashReturn.decorate(cashNormal);
        cashRebate.decorate(cashReturn);
        return cashRebate;
    }

}
