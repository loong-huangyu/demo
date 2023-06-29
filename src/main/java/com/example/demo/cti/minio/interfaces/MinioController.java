package com.example.demo.cti.minio.interfaces;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.cti.minio.application.MinioApplication;
import com.example.demo.cti.minio.application.dto.PackageFileDto;
import com.example.demo.cti.minio.domain.MergeFile;
import com.example.demo.cti.minio.domain.UploadFile;
import com.example.demo.cti.minio.domain.config.MinioConfiguration;
import com.example.demo.cti.pay.interfaces.constant.ApiPath;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rain
 * @description: minio使用
 * @date 2023/1/3 11:17 上午
 */
@RestController
@RequestMapping("/minio")
@Api(description = "minio", tags = "minio-controller")
@ApiResponses(value = {
    @ApiResponse(code = 400, message = "请求数据格式或内容不对"),
    @ApiResponse(code = 401, message = "身份验证失败"),
    @ApiResponse(code = 403, message = "权限验证失败"),
    @ApiResponse(code = 404, message = "找不到路径或 ID 所对应的资源")})
@Slf4j
public class MinioController {

    private final MinioApplication minioApplication;

    private final MinioConfiguration minioConfiguration;

    public MinioController(MinioApplication minioApplication,
        MinioConfiguration minioConfiguration) {
        this.minioApplication = minioApplication;
        this.minioConfiguration = minioConfiguration;
    }

    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/upload", consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    public void upload(HttpServletRequest request) {
        UploadFile uploadFile = new UploadFile(request);
        log.debug("上传文件内容 '{}' 文件包名 '{}' ", uploadFile.getFileName(), uploadFile.getFileHash());
        minioApplication.upload(uploadFile);
    }

    @ApiOperation(value = "合并文件")
    @PostMapping(value = "/merge", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public String merge(
        @ApiParam(value = "合并文件", required = true) @RequestBody @Valid MergeFile mergeFile) {
        log.debug("合并文件内容 '{}'", mergeFile);
        return minioApplication.merge(mergeFile);
    }

    @ApiOperation(value = "查询文件是否合并")
    @GetMapping(value = "/find/file-hash/{fileHash}", produces = APPLICATION_JSON_VALUE)
    public PackageFileDto findByFileHash(
        @ApiParam(value = "合并文件", required = true) @PathVariable String fileHash) throws Exception {
        log.debug("查询文件内容 '{}'", fileHash);
        List<String> fileUrlList = minioApplication.findByFileHash(fileHash);
        return new PackageFileDto(fileUrlList, minioConfiguration);
    }

}
