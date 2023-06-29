package com.example.demo.cti.shop.infrastructrue.util.operation;

/**
 * @author rain
 * @description: 运算
 * @date 2023/3/18 2:51 下午
 */
public class OperationFactory {

    public static Operation compute(String operation) {
        double result = 0d;
        Operation op = null;
        switch (operation) {
            case "+":
                op = new Add();
                break;
            case "-":
                op = new Sub();
                break;
            case "*":
                op = new Mul();
                break;
            case "/":
                op = new Div();
                break;
            case "pow":
                op = new Pow();
                break;
        }
        return op;
    }

}
