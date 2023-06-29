package com.example.demo.cti.minio.domain;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;

import lombok.Data;

/**
 * @author rain
 * @description: 上传文件
 * @date 2023/1/3 2:02 下午
 */
@Data
public class UploadFile {

    /**
     * blog文件获取
     */
    private MultipartFile multipartFile;

    /**
     * 整体文件名
     */
    private String fileName;

    /**
     * 文件hash(包名)
     */
    private String fileHash;

    /**
     * 分片文件名
     */
    private String hashName;

    public UploadFile(HttpServletRequest request) {
        MultipartHttpServletRequest params = (MultipartHttpServletRequest) request;
        this.multipartFile = ((MultipartHttpServletRequest) request)
            .getFile("multipartFile");
        this.fileName = params.getParameter("fileName");
        this.fileHash = params.getParameter("fileHash");
        this.hashName = params.getParameter("hashName");
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
