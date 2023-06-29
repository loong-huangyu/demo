package com.example.demo.cti.pay.application.command;

import com.alibaba.fastjson.JSON;

/**
 * 支付者信息
 */
public class WechatPayPayerCommand {

    /**
     * 用户标识
     */
    private String openid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
