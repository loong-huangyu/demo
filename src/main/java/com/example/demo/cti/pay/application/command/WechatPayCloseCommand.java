package com.example.demo.cti.pay.application.command;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 关单对象
 */
public class WechatPayCloseCommand {

    /**
     * 直连商户的商户号，由微信支付生成并下发。
     */
    @JSONField(name = "mchid")
    private String mchId;

    public WechatPayCloseCommand() {
    }

    public WechatPayCloseCommand(String mchId) {
        this.mchId = mchId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
