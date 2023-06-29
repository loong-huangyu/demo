package com.example.demo.cti.pay.application.command;

import com.alibaba.fastjson.JSON;

/**
 * 退款出资账户及金额
 */
public class WechatPayRefundAmountFromCommand {

    /**
     * 出资账户类型
     *
     * 下面枚举值多选一。
     * 枚举值：
     * AVAILABLE : 可用余额
     * UNAVAILABLE : 不可用余额
     */
    private String account = "AVAILABLE";

    /**
     * 出资金额
     */
    private int amount;

    public WechatPayRefundAmountFromCommand() {
    }

    public WechatPayRefundAmountFromCommand(String account, int amount) {
        this.account = account;
        this.amount = amount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
