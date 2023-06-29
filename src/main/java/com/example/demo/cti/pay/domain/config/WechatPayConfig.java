package com.example.demo.cti.pay.domain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "pay.wechat")
public class WechatPayConfig {

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户证书序列号
     */
    private String mchSerialNo;

    /**
     * 用于证书解密的密钥
     */
    private String apiV3Key;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 通知地址
     */
    private String callbackUrl;

    /**
     * 私钥存放地址
     */
    private String privateKeyAddress;

    /**
     * 预支付路径
     */
    private String prepayUrl;

    /**
     * 申请退款路径
     */
    private String refundUrl;

    /**
     * 查询订单路径
     */
    private String findOrderUrl;

    /**
     * 关闭订单头路径
     */
    private String closeOrderHeadUrl;

    /**
     * 关闭订单尾路径
     */
    private String closeOrderTailUrl;

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchSerialNo() {
        return mchSerialNo;
    }

    public void setMchSerialNo(String mchSerialNo) {
        this.mchSerialNo = mchSerialNo;
    }

    public String getApiV3Key() {
        return apiV3Key;
    }

    public void setApiV3Key(String apiV3Key) {
        this.apiV3Key = apiV3Key;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getPrivateKeyAddress() {
        return privateKeyAddress;
    }

    public void setPrivateKeyAddress(String privateKeyAddress) {
        this.privateKeyAddress = privateKeyAddress;
    }

    public String getPrepayUrl() {
        return prepayUrl;
    }

    public void setPrepayUrl(String prepayUrl) {
        this.prepayUrl = prepayUrl;
    }

    public String getRefundUrl() {
        return refundUrl;
    }

    public void setRefundUrl(String refundUrl) {
        this.refundUrl = refundUrl;
    }

    public String getFindOrderUrl() {
        return findOrderUrl;
    }

    public void setFindOrderUrl(String findOrderUrl) {
        this.findOrderUrl = findOrderUrl;
    }

    public String getCloseOrderHeadUrl() {
        return closeOrderHeadUrl;
    }

    public void setCloseOrderHeadUrl(String closeOrderHeadUrl) {
        this.closeOrderHeadUrl = closeOrderHeadUrl;
    }

    public String getCloseOrderTailUrl() {
        return closeOrderTailUrl;
    }

    public void setCloseOrderTailUrl(String closeOrderTailUrl) {
        this.closeOrderTailUrl = closeOrderTailUrl;
    }

}
