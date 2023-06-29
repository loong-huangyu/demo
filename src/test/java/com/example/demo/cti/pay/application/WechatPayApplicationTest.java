package com.example.demo.cti.pay.application;

import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.cti.pay.application.command.WechatPayAmountCommand;
import com.example.demo.cti.pay.application.command.WechatPayCommand;
import com.example.demo.cti.pay.application.dto.WechatPayPrepayDto;
import com.example.demo.cti.pay.application.query.WechatPayPrepayQuery;
import com.example.demo.cti.pay.application.query.WechatPayRefundQuery;
import com.example.demo.cti.pay.domain.config.WechatPayConfig;

@ExtendWith(MockitoExtension.class)
class WechatPayApplicationTest {

    private static final Logger log = LoggerFactory.getLogger(WechatPayApplicationTest.class);

    /**
     * 应用ID
     */
    private String appId = "";

    /**
     * 商户号
     */
    private String mchId = "";

    /**
     * 商户证书序列号
     */
    private String mchSerialNo = "";

    /**
     * 用于证书解密的密钥
     */
    private String apiV3Key = "";

    /**
     * 通知地址
     */
    private String callbackUrl = "https://test.api.ctirobot.com/api/park/v1/wechat/callback";

    /**
     * 私钥存放地址
     */
    private String privateKeyAddress = "pay/wechat/apiclient_key.pem";

    /**
     * 预支付路径
     * app支付路径，如果测试小程序，先获取到openID后调用
     * https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi
     */
    private String prepayUrl = "https://api.mch.weixin.qq.com/v3/pay/transactions/app";

    /**
     * 申请退款路径
     */
    private String refundUrl = "https://api.mch.weixin.qq.com/v3/refund/domestic/refunds";

    /**
     * 查询订单路径
     */
    private String findOrderUrl = "https://api.mch.weixin.qq.com/v3/pay/transactions/id/";

    /**
     * 关闭订单头路径
     */
    private String closeOrderHeadUrl = "https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/";

    /**
     * 关闭订单尾路径
     */
    private String closeOrderTailUrl = "/close";

    @InjectMocks
    private WechatPayApplication wechatPayApplication;

    private WechatPayAmountCommand amount = new WechatPayAmountCommand();

    private WechatPayCommand wechatPayCommand = new WechatPayCommand();

    @Mock
    private WechatPayConfig wechatPayConfig;

    @BeforeEach
    void setUpBefore() {
        //支付金额信息
        amount = new WechatPayAmountCommand(1, "CNY");
        String description = "测试";
        String outTradeNo = "12345678911";
        //预支付对象
        wechatPayCommand = new WechatPayCommand(appId, mchId, description, outTradeNo, amount, callbackUrl);
    }

    /**
     * 微信预支付测试
     *
     * @throws Exception
     */
    @Test
    void wechatPayTradeAppPay() throws Exception {
        when(wechatPayConfig.getMchId()).thenReturn(mchId);
        when(wechatPayConfig.getMchSerialNo()).thenReturn(mchSerialNo);
        when(wechatPayConfig.getApiV3Key()).thenReturn(apiV3Key);
        when(wechatPayConfig.getPrivateKeyAddress()).thenReturn(privateKeyAddress);
        when(wechatPayConfig.getPrepayUrl()).thenReturn(prepayUrl);
        String prepayId = wechatPayApplication.wechatPayTradeAppPay(wechatPayCommand);
        log.info("prepay_id = '{}' ", prepayId);
    }

    /**
     * 加密微信jsApi调起支付的参数
     *
     * @throws Exception
     */
    @Test
    void wechatPaySign() throws Exception {
        when(wechatPayConfig.getPrivateKeyAddress()).thenReturn(privateKeyAddress);
        WechatPayPrepayDto wechatPayPrepayDto = new WechatPayPrepayDto();
        wechatPayPrepayDto.setAppId("wx8888888888888888");
        wechatPayPrepayDto.setTimeStamp("1414561699");
        String nonceStr = "5K8264ILTKCH16CQ2502SI8ZNMTM67VS";
        wechatPayPrepayDto.setNonceStr(nonceStr);
        wechatPayPrepayDto.setPrepayId(wechatPayPrepayDto.getPrepayId() + "wx201410272009395522657a690389285100");
        String signatureStr = Stream.of(wechatPayPrepayDto.getAppId(), wechatPayPrepayDto.getTimeStamp(), nonceStr,
            wechatPayPrepayDto.getPrepayId())
            .collect(Collectors.joining("\n", "", "\n"));
        log.info("传递前台的加密signatureStr '{}' ", signatureStr);
        String paySign = wechatPayApplication.sign(signatureStr);
        log.info("加密paySign '{}' ", paySign);
        wechatPayPrepayDto.setPaySign(paySign);
        log.info("传递前台的支付对象 '{}' ", wechatPayPrepayDto);
    }

    /**
     * 微信支付退款
     *
     * @throws Exception
     */
    @Test
    void wechatPayRefund() throws Exception {
        when(wechatPayConfig.getMchId()).thenReturn(mchId);
        when(wechatPayConfig.getMchSerialNo()).thenReturn(mchSerialNo);
        when(wechatPayConfig.getApiV3Key()).thenReturn(apiV3Key);
        when(wechatPayConfig.getPrivateKeyAddress()).thenReturn(privateKeyAddress);
        when(wechatPayConfig.getRefundUrl()).thenReturn(refundUrl);
        WechatPayRefundQuery wechatPayRefundQuery = new WechatPayRefundQuery();
        //wechatPayRefundQuery.setTransactionId("4200001202202107146492148071");
        wechatPayRefundQuery.setOutTradeNo("1626252502817");
        wechatPayRefundQuery.setOutRefundNo("1626252502817");
        wechatPayRefundQuery.setReason("测试退款");
        wechatPayRefundQuery.setTotal(1);
        wechatPayRefundQuery.setRefund(1);
        Boolean bool = wechatPayApplication.getWechatPayRefund(wechatPayRefundQuery);
        log.info("bool " + bool);
    }

    /**
     * 微信支付关单
     *
     * @throws Exception
     */
    @Test
    void wechatPayClose() throws Exception {
        when(wechatPayConfig.getMchId()).thenReturn(mchId);
        when(wechatPayConfig.getMchSerialNo()).thenReturn(mchSerialNo);
        when(wechatPayConfig.getApiV3Key()).thenReturn(apiV3Key);
        when(wechatPayConfig.getPrivateKeyAddress()).thenReturn(privateKeyAddress);
        when(wechatPayConfig.getCloseOrderHeadUrl()).thenReturn(closeOrderHeadUrl);
        when(wechatPayConfig.getCloseOrderTailUrl()).thenReturn(closeOrderTailUrl);
        Boolean bool = wechatPayApplication.wechatPayClose("123456789");
        log.info("bool " + bool);
    }

    @Test
    void wechatPayPrepay() throws Exception {
        when(wechatPayConfig.getMchId()).thenReturn(mchId);
        when(wechatPayConfig.getMchSerialNo()).thenReturn(mchSerialNo);
        when(wechatPayConfig.getApiV3Key()).thenReturn(apiV3Key);
        when(wechatPayConfig.getPrivateKeyAddress()).thenReturn(privateKeyAddress);
        when(wechatPayConfig.getPrepayUrl()).thenReturn(prepayUrl);
        WechatPayPrepayQuery wechatPayPrepayQuery = new WechatPayPrepayQuery();
        wechatPayPrepayQuery.setOpenId("填写用户openId");
        wechatPayApplication.wechatPayPrepay(wechatPayPrepayQuery);
    }

}
