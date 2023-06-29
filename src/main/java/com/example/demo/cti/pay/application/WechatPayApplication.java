package com.example.demo.cti.pay.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.IdGenerator;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.cti.pay.application.command.WechatPayCloseCommand;
import com.example.demo.cti.pay.application.command.WechatPayCommand;
import com.example.demo.cti.pay.application.command.WechatPayNotifyDataCommand;
import com.example.demo.cti.pay.application.command.WechatPayOrderCommand;
import com.example.demo.cti.pay.application.command.WechatPayRefundCommand;
import com.example.demo.cti.pay.application.dto.WechatPayPrepayDto;
import com.example.demo.cti.pay.application.query.WechatPayNotifyQuery;
import com.example.demo.cti.pay.application.query.WechatPayPrepayQuery;
import com.example.demo.cti.pay.application.query.WechatPayRefundQuery;
import com.example.demo.cti.pay.application.query.WechatPayResourceQuery;
import com.example.demo.cti.pay.domain.config.WechatPayConfig;
import com.example.demo.cti.pay.infrastructure.mapstruct.WechatPayCommandMapStruct;
import com.example.demo.cti.pay.infrastructure.mapstruct.WechatPayRefundCommandMapStruct;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;

@Service
public class WechatPayApplication {

    private static final Logger log = LoggerFactory.getLogger(WechatPayApplication.class);

    private static final Integer THOUSAND = 1000;

    @Autowired
    private WechatPayConfig wechatPayConfig;

    /**
     * 获取商户私钥
     *
     * @return
     */
    public PrivateKey getPrivateKey() throws UnsupportedEncodingException {
        String privateKey = getPrivateKeyString();
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(
            new ByteArrayInputStream(privateKey.getBytes(StandardCharsets.UTF_8)));
        return merchantPrivateKey;
    }

    /**
     * 使用自动更新的签名验证器，不需要传入证书
     * mchId：商户号,mchSerialNo：商户证书序列号,merchantPrivateKey：字符串格式的商户私钥，apiV3key：V3密钥）
     *
     * @param merchantPrivateKey
     * @return
     * @throws UnsupportedEncodingException
     */
    public AutoUpdateCertificatesVerifier getResourceVerifier(
        PrivateKey merchantPrivateKey) throws UnsupportedEncodingException {
        return new AutoUpdateCertificatesVerifier(
            new WechatPay2Credentials(wechatPayConfig.getMchId(),
                new PrivateKeySigner(wechatPayConfig.getMchSerialNo(), merchantPrivateKey)),
            wechatPayConfig.getApiV3Key().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 和微信建立连接
     *
     * @return
     * @throws IOException
     */
    public CloseableHttpClient setup() throws IOException {
        // 加载商户私钥（privateKey：私钥字符串）
        PrivateKey merchantPrivateKey = getPrivateKey();
        // 先获取签名验证器
        // 再初始化httpClient,通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签
        CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
            .withMerchant(wechatPayConfig.getMchId(), wechatPayConfig.getMchSerialNo(), merchantPrivateKey)
            .withValidator(new WechatPay2Validator(getResourceVerifier(merchantPrivateKey))).build();
        return httpClient;
    }

    /**
     * APP支付统一下单（预支付订单）
     * https://api.mch.weixin.qq.com/v3/pay/transactions/app
     *
     * @param wechatPayCommand 支付参数
     * @return 带有签名的订单支付信息
     */
    public String wechatPayTradeAppPay(WechatPayCommand wechatPayCommand) throws Exception {
        log.info("开始APP支付 '{}' ", wechatPayCommand);
        //完成签名并执行请求
        CloseableHttpResponse response = wechatPayClient(wechatPayConfig.getPrepayUrl(),
            wechatPayCommand);
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpServletResponse.SC_OK) {
                //处理成功
                log.info("success,return body = '{}' ", EntityUtils.toString(response.getEntity()));
                return EntityUtils.toString(response.getEntity());
            } else if (statusCode == HttpServletResponse.SC_NO_CONTENT) {
                //处理成功，无返回Body
                log.info("success");
                throw new Exception("request success,but no body,please retry");
            } else {
                log.error("failed,resp code = '{}' ,return body = ", statusCode,
                    EntityUtils.toString(response.getEntity()));
                throw new Exception("request failed");
            }
        } catch (Exception e) {
            log.error("调用遭遇异常原因 '{}' ", e.getMessage());
            throw new Exception(e.getMessage(), e);
        } finally {
            response.close();
        }
    }

    /**
     * 微信支付成功回调
     *
     * @param response             响应
     * @param request              请求参数
     * @param wechatPayNotifyQuery 返回数据
     * @return 应答信息
     * @throws Exception 异常
     */
    public JSONObject wechatPayResultCallback(HttpServletResponse response, HttpServletRequest request,
        WechatPayNotifyQuery wechatPayNotifyQuery) {
        log.info("微信支付回调参数 '{}' ", wechatPayNotifyQuery);
        //返回通知的应答报文，code(32)：SUCCESS为清算机构接收成功；message(64)：错误原因
        JSONObject responseJson = new JSONObject();
        responseJson.put("code", "FAIL");
        //支付通知http应答码为200或204才会当作正常接收，当回调处理异常时，应答的HTTP状态码应为500，或者4xx。
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        //回调签名
        String signature = request.getHeader("Wechatpay-Signature");
        //平台证书序列号
        String serial = request.getHeader("Wechatpay-Serial");
        //应答时间戳
        String timestamp = request.getHeader("Wechatpay-Timestamp");
        //应答随机串
        String nonce = request.getHeader("Wechatpay-Nonce");
        if (ObjectUtils.isEmpty(signature) || ObjectUtils.isEmpty(serial)
            || ObjectUtils.isEmpty(timestamp) || ObjectUtils.isEmpty(nonce)) {
            log.info("微信支付回调请求header缺失");
            responseJson.put("message", "回调请求header缺失");
            return responseJson;
        }
        //获取签名串，验签
        String srcData = timestamp + "\n" + nonce + "\n" + JSON.toJSONString(wechatPayNotifyQuery) + "\n";
        try {
            if (autoUpdateVerifier(serial, srcData, signature)) {
                log.info("验签失败 '{}' ", srcData);
                responseJson.put("message", "验签失败");
                return responseJson;
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.info("验签失败IOException '{}' ", e);
            responseJson.put("message", "验签失败");
            return responseJson;
        }
        //通知资源数据
        WechatPayResourceQuery wechatPayResourceDTO = wechatPayNotifyQuery.getResource();
        //对密文串进行解密
        AesUtil aesUtil = new AesUtil(wechatPayConfig.getApiV3Key().getBytes());
        String verify = null;
        try {
            verify = aesUtil
                .decryptToString(wechatPayResourceDTO.getAssociatedData().getBytes(StandardCharsets.UTF_8),
                    wechatPayResourceDTO.getNonce().getBytes(StandardCharsets.UTF_8),
                    wechatPayResourceDTO.getCiphertext());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            log.info("解密失败GeneralSecurityException '{}' ", e);
            responseJson.put("message", "解密失败");
            return responseJson;
        } catch (IOException e) {
            e.printStackTrace();
            log.info("解密失败IOException '{}' ", e);
            responseJson.put("message", "解密失败");
            return responseJson;
        }
        WechatPayNotifyDataCommand wechatPayNotifyDataCommand =
            JSONObject.parseObject(verify, WechatPayNotifyDataCommand.class);
        log.debug("对密文串进行解密后的数据 '{}' ", wechatPayNotifyDataCommand);
        if (ObjectUtils.isEmpty(wechatPayNotifyDataCommand.getTransactionId())) {
            responseJson.put("message", "微信支付成功，未接受到微信订单号");
            return responseJson;
        }
        if (ObjectUtils.isEmpty(wechatPayNotifyDataCommand.getOutTradeNo())) {
            responseJson.put("message", "微信支付成功，未接受到商户订单号");
            return responseJson;
        }
        //todo 传递wechatPayNotifyDataCommand 支付成功，更改订单相关信息，同步调用订单支付事件的接口
        Boolean result = true;
        if (result) {
            log.info("微信支付结果通知：支付成功，处理成功。订单号为 '' ", wechatPayNotifyDataCommand.getOutTradeNo());
            response.setStatus(HttpServletResponse.SC_OK);
            responseJson.put("code", "SUCCESS");
            responseJson.put("message", "微信支付成功，商户处理成功");
        } else {
            log.info("微信支付结果通知：支付成功，处理失败。订单号为：", wechatPayNotifyDataCommand.getOutTradeNo());
            responseJson.put("message", "微信支付成功，商户处理失败");
        }
        return responseJson;
    }

    /**
     * 微信回调验签
     *
     * @param reqData            待签名签名串
     * @param wechatPaySignature 微信回调签名
     * @return 是与否
     * @throws IOException 异常
     */
    public Boolean autoUpdateVerifier(String serialNumber, String reqData,
        String wechatPaySignature) throws IOException {
        //自动更新证书验签
        AutoUpdateCertificatesVerifier verifier = getResourceVerifier(getPrivateKey());
        return verifier.verify(serialNumber, reqData.getBytes(StandardCharsets.UTF_8), wechatPaySignature);
    }

    /**
     * 申请退款
     *
     * @param wechatPayRefundCommand 退款传递参数
     * @return
     */
    public Boolean wechatPayTradeRefund(WechatPayRefundCommand wechatPayRefundCommand) throws Exception {
        //完成签名并执行请求
        CloseableHttpResponse response = wechatPayClient(wechatPayConfig.getRefundUrl(), wechatPayRefundCommand);
        log.info("response '{}' ", response);
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpServletResponse.SC_OK) {
                //退款处理成功
                log.info("success,return body = '{}' ", response.getEntity());
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.close();
        }
        return Boolean.FALSE;
    }

    /**
     * 关闭订单
     *
     * @param outTradeNo 商户系统内部订单号
     * @return
     */
    public Boolean wechatPayClose(String outTradeNo) throws Exception {
        //完成签名并执行请求
        CloseableHttpResponse response = wechatPayClient(
            wechatPayConfig.getCloseOrderHeadUrl() + outTradeNo + wechatPayConfig.getCloseOrderTailUrl(),
            new WechatPayCloseCommand(wechatPayConfig.getMchId()));
        try {
            log.debug("关闭订单 '{}' 推送成功结果 '{}' ", outTradeNo, response);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpServletResponse.SC_NO_CONTENT) {
                log.info("关闭订单成功 '{}' ", outTradeNo);
                return Boolean.TRUE;
            } else if (statusCode == HttpServletResponse.SC_ACCEPTED) {
                log.info("关闭订单失败,用户正在支付 '{}' ", outTradeNo);
                return Boolean.TRUE;
            }
            log.info("关闭订单失败 '{}' ,失败code '{}' ", outTradeNo, statusCode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.close();
        }

        return Boolean.FALSE;
    }

    /**
     * post访问微信服务
     *
     * @param url     访问地址
     * @param reqData 访问数据
     * @return
     * @throws Exception
     */
    public CloseableHttpResponse wechatPayClient(String url, Object reqData) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(JSONObject.toJSONString(reqData),
            ContentType.create("application/json", StandardCharsets.UTF_8));
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        //初始化httpClient
        CloseableHttpClient httpClient = setup();
        //完成签名并执行请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return response;
    }

    /**
     * 测试根据openId生成，微信支付的值
     *
     * @param wechatPayPrepayQuery
     * @return
     */
    public WechatPayPrepayDto wechatPayPrepay(WechatPayPrepayQuery wechatPayPrepayQuery) throws Exception {
        WechatPayOrderCommand wechatPayOrderCommand = new WechatPayOrderCommand();
        wechatPayOrderCommand.setOutTradeNo(String.valueOf(System.currentTimeMillis()));
        wechatPayOrderCommand.setTotal(1);
        wechatPayOrderCommand.setOpenId(wechatPayPrepayQuery.getOpenId());
        wechatPayOrderCommand.setDescription("测试订单");
        wechatPayOrderCommand.setAttach("测试订单附加数据");
        return wechatPayGetPrepayId(wechatPayOrderCommand);
    }

    /**
     * 小程序支付参数加密
     *
     * @param message
     * @return
     * @throws Exception
     */
    public String sign(String message) throws Exception {
        String privateKey = getPrivateKeyString();
        privateKey = privateKey.replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replaceAll("\\s+", "");
        log.info("privateKey '{}' ", privateKey);
        PrivateKey merchantPrivateKey = PemUtil
            .loadPrivateKey(new ByteArrayInputStream(privateKey.getBytes(StandardCharsets.UTF_8)));
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(merchantPrivateKey);
        sign.update(message.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(sign.sign());
    }

    /**
     * 获取私钥String
     *
     * @return
     */
    public String getPrivateKeyString() {
        String privateKey = "";
        try {
            InputStream stream = this.getClass().getClassLoader()
                .getResourceAsStream(wechatPayConfig.getPrivateKeyAddress());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            String lineTXT = null;
            while ((lineTXT = bufferedReader.readLine()) != null) {
                privateKey += lineTXT;
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    /**
     * 根据订单必填值对象获取预支付订单id
     *
     * @param wechatPayOrderCommand
     * @return
     */
    private WechatPayPrepayDto wechatPayGetPrepayId(WechatPayOrderCommand wechatPayOrderCommand) throws Exception {
        WechatPayCommand wechatPayCommand =
            WechatPayCommandMapStruct.INSTANCE.wechatPayOrderCommandToWechatPayCommand(wechatPayOrderCommand);
        wechatPayCommand.initData(wechatPayConfig, wechatPayOrderCommand);
        String prepayIdJson = wechatPayTradeAppPay(wechatPayCommand);
        JSONObject jsonObject = JSON.parseObject(prepayIdJson);
        String prepayId = jsonObject.getString("prepay_id");
        //初始化WechatPayPrepayDto
        WechatPayPrepayDto wechatPayPrepayDto = new WechatPayPrepayDto();
        wechatPayPrepayDto.setAppId(wechatPayConfig.getAppId());
        wechatPayPrepayDto.setTimeStamp(String.valueOf(System.currentTimeMillis() / THOUSAND));
        IdGenerator idGenerator = new AlternativeJdkIdGenerator();
        String nonceStr = idGenerator.generateId()
            .toString()
            .replaceAll("-", "");
        wechatPayPrepayDto.setNonceStr(nonceStr);
        wechatPayPrepayDto.setPrepayId("prepay_id=" + prepayId);
        String signatureStr = Stream.of(wechatPayPrepayDto.getAppId(), wechatPayPrepayDto.getTimeStamp(), nonceStr,
            wechatPayPrepayDto.getPrepayId())
            .collect(Collectors.joining("\n", "", "\n"));
        log.info("传递前台的加密signatureStr '{}' ", signatureStr);
        String paySign = sign(signatureStr);
        wechatPayPrepayDto.setPaySign(paySign);
        log.info("传递前台的支付对象 '{}' ", wechatPayPrepayDto);
        return wechatPayPrepayDto;
    }

    /**
     * 封装退款的接口
     *
     * @param wechatPayRefundQuery 退款参数
     * @return
     * @throws Exception
     */
    public Boolean getWechatPayRefund(WechatPayRefundQuery wechatPayRefundQuery) throws Exception {
        log.debug("微信退款数据wechatPayRefundQuery '{}' ", wechatPayRefundQuery);
        WechatPayRefundCommand wechatPayRefundCommand = WechatPayRefundCommandMapStruct.INSTANCE
            .wechatPayRefundQueryToWechatPayRefundCommand(wechatPayRefundQuery);
        log.debug("微信退款数据wechatPayRefundCommand '{}' ", wechatPayRefundCommand);
        return wechatPayTradeRefund(wechatPayRefundCommand);
    }

}
