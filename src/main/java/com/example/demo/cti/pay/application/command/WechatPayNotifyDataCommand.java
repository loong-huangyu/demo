package com.example.demo.cti.pay.application.command;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 微信支付异步通知传参-解密后的通知数据
 */
public class WechatPayNotifyDataCommand {

    /**
     * 应用ID
     */
    @JSONField(name = "appid")
    private String appId;

    /**
     * 商户号
     */
    @JSONField(name = "mchid")
    private String mchId;

    /**
     * 商户订单号
     */
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 微信支付订单号
     */
    @JSONField(name = "transaction_id")
    private String transactionId;

    /**
     * 交易类型
     */
    @JSONField(name = "trade_type")
    private String tradeType;

    /**
     * 交易状态
     */
    @JSONField(name = "trade_state")
    private String tradeState;

    /**
     * 交易状态描述
     */
    @JSONField(name = "trade_state_desc")
    private String tradeStateDesc;

    /**
     * 付款银行
     */
    @JSONField(name = "bank_type")
    private String bankType;

    /**
     * 附加数据
     */
    private String attach;

    /**
     * 支付完成时间
     */
    @JSONField(name = "success_time")
    private String successTime;

    /**
     * 支付者信息
     */
    private WechatPayPayerCommand payer;

    /**
     * 订单金额信息
     */
    private WechatPayNotifyAmountCommand amount;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public String getTradeStateDesc() {
        return tradeStateDesc;
    }

    public void setTradeStateDesc(String tradeStateDesc) {
        this.tradeStateDesc = tradeStateDesc;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(String successTime) {
        this.successTime = successTime;
    }

    public WechatPayPayerCommand getPayer() {
        return payer;
    }

    public void setPayer(WechatPayPayerCommand payer) {
        this.payer = payer;
    }

    public WechatPayNotifyAmountCommand getAmount() {
        return amount;
    }

    public void setAmount(WechatPayNotifyAmountCommand amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
