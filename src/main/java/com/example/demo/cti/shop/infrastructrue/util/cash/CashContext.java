package com.example.demo.cti.shop.infrastructrue.util.cash;

import com.example.demo.cti.shop.infrastructrue.util.cash.base.Sale;
import com.example.demo.cti.shop.infrastructrue.util.cash.factory.CashRebateReturnFactory;
import com.example.demo.cti.shop.infrastructrue.util.cash.factory.CashReturnRebateFactory;
import com.example.demo.cti.shop.infrastructrue.util.cash.factory.SaleFactory;

/**
 * @author rain
 * @description:
 * @date 2023/3/18 3:40 下午
 */
public class CashContext {

    private Sale sale;

    public CashContext(int cashType) {
        SaleFactory saleFactory = null;
        switch (cashType) {
            case 1:
                saleFactory = new CashRebateReturnFactory(1, 0, 0);
                break;
            case 2:
                saleFactory = new CashRebateReturnFactory(0.8, 0, 0);
                break;
            case 3:
                saleFactory = new CashRebateReturnFactory(0.7, 0, 0);
                break;
            case 4:
                saleFactory = new CashRebateReturnFactory(1, 300d, 100d);
                break;
            case 5:
                saleFactory = new CashRebateReturnFactory(0.8, 300d, 100d);
                break;
            case 6:
                saleFactory = new CashReturnRebateFactory(0.8, 300d, 100d);
                break;
        }
        this.sale = saleFactory.createSalesModel();
    }

    public double getResult(double price, double num) {
        return this.sale.acceptCash(price, num);
    }

}
