package com.example.demo.cti.pay.application.command;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 订单金额信息
 */
public class WechatPayNotifyAmountCommand {

    /**
     * 支付金额，单位为分
     */
    private Integer total;

    /**
     * 用户支付金额，单位为分
     */
    @JSONField(name = "payer_total")
    private Integer payerTotal;

    /**
     * CNY：人民币，境内商户号仅支持人民币。
     */
    private String currency;

    /**
     * 用户支付币种
     */
    @JSONField(name = "payer_currency")
    private String payerCurrency;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPayerTotal() {
        return payerTotal;
    }

    public void setPayerTotal(Integer payerTotal) {
        this.payerTotal = payerTotal;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayerCurrency() {
        return payerCurrency;
    }

    public void setPayerCurrency(String payerCurrency) {
        this.payerCurrency = payerCurrency;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
