package com.example.demo.cti.pay.application.query;

import com.alibaba.fastjson.JSON;

public class WechatPayPrepayQuery {

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
