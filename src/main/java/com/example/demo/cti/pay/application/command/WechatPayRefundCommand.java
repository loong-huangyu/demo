package com.example.demo.cti.pay.application.command;

import javax.validation.constraints.NotBlank;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 微信支付退款
 */
public class WechatPayRefundCommand {

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
     * 订单退款金额信息
     */
    private WechatPayRefundAmountCommand amount;

    /**
     * 退款商品信息
     */
    @JSONField(name = "goods_detail")
    private List<WechatPayRefundGoodsDetailCommand> goodsDetail;

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

    public WechatPayRefundAmountCommand getAmount() {
        return amount;
    }

    public void setAmount(WechatPayRefundAmountCommand amount) {
        this.amount = amount;
    }

    public List<WechatPayRefundGoodsDetailCommand> getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(
        List<WechatPayRefundGoodsDetailCommand> goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
