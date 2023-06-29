package com.example.demo.cti.user;

import com.alibaba.fastjson.JSON;

import lombok.Getter;

/**
 * @author rain
 * @description:
 * @date 2022/10/12 10:06 上午
 */
@Getter
public class UserEvent extends AbstractEvent {

    private User user;

    public UserEvent(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
