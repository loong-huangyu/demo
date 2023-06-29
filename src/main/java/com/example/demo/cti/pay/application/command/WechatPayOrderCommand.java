package com.example.demo.cti.pay.application.command;

import javax.validation.constraints.NotBlank;

import com.alibaba.fastjson.JSON;

/**
 * 订单必填值
 */
public class WechatPayOrderCommand {

    /**
     * 商户订单号
     */
    @NotBlank
    private String outTradeNo;

    /**
     * 支付金额，单位为分
     */
    @NotBlank
    private Integer total;

    /**
     * 用户标识
     * 用户在直连商户appid下的唯一标识。 下单前需获取到用户的Openid
     */
    @NotBlank
    private String openId;

    /**
     * 商品描述
     */
    @NotBlank
    private String description;

    /**
     * 订单失效时间
     * 遵循rfc3339标准格式，格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE，YYYY-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，
     * HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。
     * 例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。
     * 示例值：2018-06-08T10:34:56+08:00
     */
    private String timeExpire;

    /**
     * 附加数据
     * 在查询API和支付通知中原样返回，可作为自定义参数使用
     */
    private String attach;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
