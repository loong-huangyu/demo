package com.example.demo.cti.pay.application.query;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 微信支付异步通知传参-通知数据
 */
public class WechatPayResourceQuery {

    /**
     * 加密算法类型
     */
    private String algorithm;

    /**
     * 数据密文
     */
    private String ciphertext;

    /**
     * 附加数据
     */
    @JsonProperty("associated_data")
    private String associatedData;

    /**
     * 原始类型
     */
    @JsonProperty("original_type")
    private String originalType;

    /**
     * 随机串
     */
    private String nonce;

    public WechatPayResourceQuery() {
    }

    public WechatPayResourceQuery(String algorithm, String ciphertext, String associatedData, String originalType,
        String nonce) {
        this.algorithm = algorithm;
        this.ciphertext = ciphertext;
        this.associatedData = associatedData;
        this.originalType = originalType;
        this.nonce = nonce;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }

    public String getAssociatedData() {
        return associatedData;
    }

    public void setAssociatedData(String associatedData) {
        this.associatedData = associatedData;
    }

    public String getOriginalType() {
        return originalType;
    }

    public void setOriginalType(String originalType) {
        this.originalType = originalType;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
