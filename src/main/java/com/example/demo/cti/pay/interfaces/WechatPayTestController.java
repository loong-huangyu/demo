package com.example.demo.cti.pay.interfaces;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.cti.pay.application.WechatPayApplication;
import com.example.demo.cti.pay.application.dto.WechatPayPrepayDto;
import com.example.demo.cti.pay.application.query.WechatPayPrepayQuery;
import com.example.demo.cti.pay.application.query.WechatPayRefundQuery;
import com.example.demo.cti.pay.interfaces.constant.ApiPath;

@RestController
@RequestMapping(ApiPath.WECHAT_SCHEDULING_TEST_V1)
public class WechatPayTestController {

    @Autowired
    private WechatPayApplication wechatPayApplication;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public WechatPayPrepayDto wechatPayPrepay(@RequestBody WechatPayPrepayQuery wechatPayPrepayQuery) throws Exception {
        return wechatPayApplication.wechatPayPrepay(wechatPayPrepayQuery);
    }

    @PostMapping(value = "/refund",consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public JSONObject wechatPayRefund(@RequestBody WechatPayRefundQuery wechatPayRefundQuery) throws Exception {
        Boolean bool = wechatPayApplication.getWechatPayRefund(wechatPayRefundQuery);
        JSONObject responseJson = new JSONObject();
        responseJson.put("code", bool);
        return responseJson;
    }

}
