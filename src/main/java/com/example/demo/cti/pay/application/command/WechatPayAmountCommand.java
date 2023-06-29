package com.example.demo.cti.pay.application.command;

import com.alibaba.fastjson.JSON;

/**
 * 订单金额信息
 */
public class WechatPayAmountCommand {

    /**
     * 支付金额，单位为分
     */
    private Integer total;

    /**
     * CNY：人民币，境内商户号仅支持人民币。
     */
    private String currency = "CNY";

    public WechatPayAmountCommand() {
    }

    public WechatPayAmountCommand(Integer total, String currency) {
        this.total = total;
        this.currency = currency;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
