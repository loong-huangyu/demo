package com.example.demo.cti.user;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author rain
 * @description:
 * @date 2022/10/12 9:50 上午
 */
@Data
@AllArgsConstructor
public class Name implements Serializable {

    private String first;

    private String second;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
