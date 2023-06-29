package com.example.demo.cti.shop;

import org.junit.Test;

import com.example.demo.cti.shop.infrastructrue.util.cash.CashContext;
import com.example.demo.cti.shop.infrastructrue.util.operation.Operation;
import com.example.demo.cti.shop.infrastructrue.util.operation.OperationFactory;

/**
 * @author rain
 * @description: 运算
 * @date 2023/3/12 6:45 下午
 */
public class ShopTest {

    @Test
    public void shopNumTest() {
        double[] num = new double[]{2.0, 1.0};
        String operation = "*";
        Operation compute = OperationFactory.compute(operation);
        double result = compute.compute(num[0], num[1]);
        System.out.println("结果 ： " + result);
    }

    @Test
    public void shopCashTest() {
        double[] num = new double[]{2000.0, 1.0, 0.8};
        CashContext cashContext = new CashContext(5);
        double result = cashContext.getResult(num[0], num[1]);
        System.out.println("结果 ： " + result);
    }

}
