package com.example.demo.cti.pay.application.query;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 微信支付异步通知传参
 */
public class WechatPayNotifyQuery {

    /**
     * 通知ID
     */
    private String id;

    /**
     * 通知创建时间
     */
    @JsonProperty("create_time")
    private String createTime;

    /**
     * 通知类型
     */
    @JsonProperty("event_type")
    private String eventType;

    /**
     * 通知数据类型
     */
    @JsonProperty("resource_type")
    private String resourceType;

    /**
     * 通知数据
     */
    private WechatPayResourceQuery resource;

    /**
     * 回调摘要
     */
    private String summary;

    public WechatPayNotifyQuery() {
    }

    public WechatPayNotifyQuery(String id, String createTime, String eventType, String resourceType,
        WechatPayResourceQuery resource, String summary) {
        this.id = id;
        this.createTime = createTime;
        this.eventType = eventType;
        this.resourceType = resourceType;
        this.resource = resource;
        this.summary = summary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public WechatPayResourceQuery getResource() {
        return resource;
    }

    public void setResource(WechatPayResourceQuery resource) {
        this.resource = resource;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
