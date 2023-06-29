package com.example.demo.cti.pay.application.command;

import javax.validation.constraints.NotBlank;

import com.alibaba.fastjson.JSON;

/**
 * 订单退款金额信息
 */
public class WechatPayRefundAmountCommand {

    /**
     * 原支付交易的订单总金额，币种的最小单位，只能为整数。
     * 原订单金额，单位为分
     */
    @NotBlank
    private Integer total;

    /**
     * 退款金额，单位为分
     */
    @NotBlank
    private Integer refund;

    /**
     * 符合ISO 4217标准的三位字母代码，目前只支持人民币：CNY
     */
    @NotBlank
    private String currency = "CNY";

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getRefund() {
        return refund;
    }

    public void setRefund(Integer refund) {
        this.refund = refund;
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
