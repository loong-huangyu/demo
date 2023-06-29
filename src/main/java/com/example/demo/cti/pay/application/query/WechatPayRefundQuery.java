package com.example.demo.cti.pay.application.query;

import javax.validation.constraints.NotBlank;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 微信退款对象封装
 */
public class WechatPayRefundQuery {

    /**
     * 微信支付订单号(商户订单号,微信支付订单号,二选一)
     */
    @JSONField(name = "transaction_id")
    private String transactionId;

    /**
     * 商户订单号(商户订单号,微信支付订单号,二选一)
     */
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 商户退款单号
     */
    @NotBlank
    @JSONField(name = "out_refund_no")
    private String outRefundNo;

    /**
     * 退款原因
     */
    private String reason;

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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
