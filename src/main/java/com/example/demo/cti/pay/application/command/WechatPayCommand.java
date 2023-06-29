package com.example.demo.cti.pay.application.command;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.example.demo.cti.pay.domain.config.WechatPayConfig;

/**
 * JSAPI下单对象
 * 微信支付服务后台生成预支付交易单
 */
public class WechatPayCommand {

    /**
     * 应用ID
     */
    @JSONField(name = "appid")
    private String appId;

    /**
     * 直连商户号
     */
    @JSONField(name = "mchid")
    private String mchId;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商户订单号
     */
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 订单失效时间
     * 遵循rfc3339标准格式，格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE，YYYY-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，
     * HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。
     * 例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。
     * 示例值：2018-06-08T10:34:56+08:00
     */
    @JSONField(name = "time_expire")
    private String timeExpire;

    /**
     * 附加数据
     * 在查询API和支付通知中原样返回，可作为自定义参数使用
     */
    private String attach;

    /**
     * 通知地址
     * 通知URL必须为直接可访问的URL，不允许携带查询串，要求必须为https地址。
     */
    @JSONField(name = "notify_url")
    private String notifyUrl;

    /**
     * 支付金额信息
     */
    private WechatPayAmountCommand amount;

    /**
     * 支付者
     */
    private WechatPayPrepayPayerCommand payer;

    public WechatPayCommand() {
    }

    public WechatPayCommand(String appId, String mchId, String description, String outTradeNo,
        WechatPayAmountCommand amount, String notifyUrl) {
        this.appId = appId;
        this.mchId = mchId;
        this.description = description;
        this.outTradeNo = outTradeNo;
        this.amount = amount;
        this.notifyUrl = notifyUrl;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public WechatPayAmountCommand getAmount() {
        return amount;
    }

    public void setAmount(WechatPayAmountCommand amount) {
        this.amount = amount;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public WechatPayPrepayPayerCommand getPayer() {
        return payer;
    }

    public void setPayer(WechatPayPrepayPayerCommand payer) {
        this.payer = payer;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public void initData(WechatPayConfig wechatPayConfig, WechatPayOrderCommand wechatPayOrderCommand) {
        this.appId = wechatPayConfig.getAppId();
        this.mchId = wechatPayConfig.getMchId();
        this.notifyUrl = wechatPayConfig.getCallbackUrl();
    }

}
