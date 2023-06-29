package com.example.demo.cti.pay.application.command;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 退款商品对象
 */
public class WechatPayRefundGoodsDetailCommand {

    /**
     * 商户侧商品编码
     */
    @JSONField(name = "merchant_goods_id")
    private String merchantGoodsId;

    /**
     * 微信侧商品编码
     */
    @JSONField(name = "wechatpay_goods_id")
    private String wechatPayGoodsId;

    /**
     * 商品名称
     */
    @JSONField(name = "goods_name")
    private String goodsName;

    /**
     * 商品单价
     */
    @JSONField(name = "unit_price")
    private int unitPrice;

    /**
     * 商品退款金额
     */
    @JSONField(name = "refund_amount")
    private int refundAmount;

    /**
     * 商品退货数量
     */
    @JSONField(name = "refund_quantity")
    private int refundQuantity;

    public WechatPayRefundGoodsDetailCommand() {
    }

    public WechatPayRefundGoodsDetailCommand(String merchantGoodsId, int unitPrice, int refundAmount,
        int refundQuantity) {
        this.merchantGoodsId = merchantGoodsId;
        this.unitPrice = unitPrice;
        this.refundAmount = refundAmount;
        this.refundQuantity = refundQuantity;
    }

    public String getMerchantGoodsId() {
        return merchantGoodsId;
    }

    public void setMerchantGoodsId(String merchantGoodsId) {
        this.merchantGoodsId = merchantGoodsId;
    }

    public String getWechatPayGoodsId() {
        return wechatPayGoodsId;
    }

    public void setWechatPayGoodsId(String wechatPayGoodsId) {
        this.wechatPayGoodsId = wechatPayGoodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(int refundAmount) {
        this.refundAmount = refundAmount;
    }

    public int getRefundQuantity() {
        return refundQuantity;
    }

    public void setRefundQuantity(int refundQuantity) {
        this.refundQuantity = refundQuantity;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
