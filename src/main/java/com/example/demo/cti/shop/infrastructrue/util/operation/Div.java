package com.example.demo.cti.shop.infrastructrue.util.operation;

/**
 * @author rain
 * @description:
 * @date 2023/3/18 3:00 下午
 */
public class Div extends Operation{

    @Override
    public double compute(double one, double two) {
        if (two == 0){
            System.out.println("除数不能为0");
            throw new ArithmeticException();
        }
        return one/two;
    }

}
