package com.example.demo.cti.pay.application.command;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 预支付订单用户
 */
public class WechatPayPrepayPayerCommand {

    /**
     * 用户标识
     * 用户在直连商户appid下的唯一标识。 下单前需获取到用户的Openid
     */
    @JSONField(name = "openid")
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
