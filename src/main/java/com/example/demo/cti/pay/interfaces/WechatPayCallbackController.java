package com.example.demo.cti.pay.interfaces;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.cti.pay.application.WechatPayApplication;
import com.example.demo.cti.pay.application.query.WechatPayNotifyQuery;
import com.example.demo.cti.pay.interfaces.constant.ApiPath;

@RestController
@RequestMapping(ApiPath.WECHAT_SCHEDULING_CALL_BACK_V1)
public class WechatPayCallbackController {

    @Autowired
    private WechatPayApplication wechatPayApplication;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public JSONObject wechatPayResultCallback(HttpServletResponse response, HttpServletRequest request,
        @RequestBody WechatPayNotifyQuery wechatPayNotifyQuery) {
        return wechatPayApplication.wechatPayResultCallback(response, request, wechatPayNotifyQuery);
    }

}
