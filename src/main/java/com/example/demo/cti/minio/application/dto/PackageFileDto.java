package com.example.demo.cti.minio.application.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.demo.cti.minio.domain.config.MinioConfiguration;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rain
 * @description: 包内文件
 * @date 2023/1/31 10:49 上午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "包内文件")
public class PackageFileDto {

    @ApiModelProperty(value = "存在合并文件")
    private boolean existenceMergeFile = false;

    @ApiModelProperty(value = "文件路径")
    private List<String> fileUrlList = new ArrayList<>();

    public PackageFileDto(List<String> fileUrlList, MinioConfiguration minioConfiguration) {
        fileUrlList.forEach(fileUrl -> {
            if (fileUrl.contains(".")) {
                this.existenceMergeFile = true;
                this.fileUrlList = Collections.singletonList(minioConfiguration.fileUrl(fileUrl));
            } else {
                String[] file = fileUrl.split("/");
                this.fileUrlList.add(file[file.length - 1]);
            }
        });
    }

}
