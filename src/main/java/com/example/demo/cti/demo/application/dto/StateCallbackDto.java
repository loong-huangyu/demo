package com.example.demo.cti.demo.application.dto;

import com.alibaba.fastjson.JSON;

import lombok.Data;

/**
 * @author rain
 * @description:
 * @date 2022/6/20 3:06 下午
 */
@Data
public class StateCallbackDto {

    /**
     * 单据号
     */
    private String originId;

    /**
     * 起点科室
     */
    private String startDepartmentName;

    /**
     * 终点科室
     */
    private String endDepartmentName;

    /**
     * 订单状态
     */
    private String orderState;

    /**
     * 柜子名称
     */
    private String hiveName;

    /**
     * 格口名称
     */
    private String lockerName;

    /**
     * 当前时间戳
     */
    private Long currentTime;

    /**
     * 机器人名称
     */
    private String robotName;

    /**
     * 发起人
     */
    private String requestUserName;

    /**
     * 取件人
     */
    private String collectedUserName;

    /**
     * 回调路径
     */
    private String callbackUrl;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
