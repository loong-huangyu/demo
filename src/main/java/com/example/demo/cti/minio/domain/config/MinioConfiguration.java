package com.example.demo.cti.minio.domain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @author rain
 * @description:
 * @date 2023/1/3 2:07 下午
 */
@Configuration
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioConfiguration {

    /**
     * 存放地址
     */
    private String endpoint;

    /**
     * 访问地址
     */
    private String readEndPoint;

    /**
     * 账户
     */
    private String accessKeyId;

    /**
     * 密码
     */
    private String accessKeySecret;

    /**
     * 桶名称
     */
    private String bucketName;

    /**
     * 包前缀
     */
    private String prefix;

    public String fileUrl(String fileName) {
        return this.readEndPoint + "/" + this.bucketName + "/" + fileName;
    }

    public String getFileName(String packageName, String fileName) {
        return this.prefix + packageName + "/" + fileName;
    }

}
