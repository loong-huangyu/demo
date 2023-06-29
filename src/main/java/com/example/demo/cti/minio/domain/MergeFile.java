package com.example.demo.cti.minio.domain;

import com.alibaba.fastjson.JSON;

import lombok.Data;

/**
 * @author rain
 * @description: 合并文件
 * @date 2023/1/4 11:11 上午
 */
@Data
public class MergeFile {

    /**
     * 整体文件名
     */
    private String fileName;

    /**
     * 文件hash(包名)
     */
    private String fileHash;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
