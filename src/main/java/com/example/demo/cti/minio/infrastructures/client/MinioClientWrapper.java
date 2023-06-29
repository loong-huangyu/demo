package com.example.demo.cti.minio.infrastructures.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.cti.minio.domain.MergeFile;
import com.example.demo.cti.minio.domain.UploadFile;
import com.example.demo.cti.minio.domain.config.MinioConfiguration;

import io.minio.ComposeObjectArgs;
import io.minio.ComposeSource;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rain
 * @description: MinIO客户端
 * @date 2023/1/3 2:10 下午
 */
@Service
@Slf4j
public class MinioClientWrapper {

    private final MinioConfiguration minioConfiguration;

    public MinioClientWrapper(MinioConfiguration minioConfiguration) {
        this.minioConfiguration = minioConfiguration;
    }

    /**
     * 文件上传
     *
     * @param uploadFile
     * @return
     */
    public String uploadFile(UploadFile uploadFile) {
        String fileName = minioConfiguration.getFileName(uploadFile.getFileHash(), uploadFile.getHashName());
        upload(fileName, uploadFile.getMultipartFile());
        return minioConfiguration.fileUrl(fileName);
    }

    /**
     * 合并分片文件
     *
     * @param mergeFile
     * @return
     */
    public String fileMerge(MergeFile mergeFile) {
        String fileName = minioConfiguration.getFileName(mergeFile.getFileHash(), mergeFile.getFileName());
        List<String> fileNames = merge(mergeFile.getFileHash(), fileName);
        deleteObject(fileNames);
        return minioConfiguration.fileUrl(fileName);
    }

    /**
     * 上传文件
     *
     * @param fileName      包名+文件名
     * @param multipartFile 文件流
     */
    public void upload(String fileName, MultipartFile multipartFile) {
        MinioClient minioClient = getMinioClient();
        try {
            log.debug("上传到minio桶 桶名称'{}' 文件路径'{} ",
                minioConfiguration.getBucketName(), fileName);
            minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioConfiguration.getBucketName())
                .object(fileName)
                .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                .contentType("application/octet-stream")
                .build());
        } catch (Exception e) {
            log.warn("上传到minio桶失败 桶名称'{}' 文件路径'{} ", minioConfiguration.getBucketName(), fileName, e);
        }
    }

    @NotNull
    private MinioClient getMinioClient() {
        return MinioClient.builder()
            .endpoint(minioConfiguration.getEndpoint())
            .credentials(minioConfiguration.getAccessKeyId(), minioConfiguration.getAccessKeySecret())
            .build();
    }

    /**
     * 将块文件合并到新桶,合并完后删除
     *
     * @param packageName 存旧文件的包名
     * @param fileName    存到新桶中的包名+文件名称
     * @return
     */
    public List<String> merge(String packageName, String fileName) {
        try {
            List<ComposeSource> sourceObjectList = new ArrayList<ComposeSource>();
            List<String> fileNames = this.getFolderList(packageName);
            log.debug("需要合并的文件名 '{}' ", fileNames.toString());
            if (!fileNames.isEmpty()) {
                for (String name : fileNames) {
                    sourceObjectList.add(ComposeSource.builder()
                        .bucket(minioConfiguration.getBucketName())
                        .object(name).build());
                }
            }
            MinioClient minioClient = getMinioClient();
            minioClient.composeObject(
                ComposeObjectArgs.builder()
                    .bucket(minioConfiguration.getBucketName())
                    .object(fileName)
                    .sources(sourceObjectList)
                    .build());
            return fileNames;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 删除临时文件
     *
     * @param fileNames 准备删除的文件名列表
     */
    private void deleteObject(List<String> fileNames) {
        log.debug("准备删除的文件名 '{}' ", fileNames.toString());
        fileNames.forEach(objectName -> {
            MinioClient minioClient = getMinioClient();
            try {
                minioClient.removeObject(
                    RemoveObjectArgs.builder()
                        .bucket(minioConfiguration.getBucketName())
                        .object(objectName)
                        .build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取指定packageName下所有文件 文件名
     *
     * @param packageName
     * @return
     * @throws Exception
     */
    public List<String> getFolderList(String packageName) throws Exception {
        String prefixNames = minioConfiguration.getPrefix() + packageName + "/";
        MinioClient minioClient = getMinioClient();
        Iterable<Result<Item>> results = minioClient
            .listObjects(ListObjectsArgs.builder()
                .bucket(minioConfiguration.getBucketName())
                .prefix(prefixNames)
                .recursive(false)
                .build());
        Iterator<Result<Item>> iterator = results.iterator();
        List<String> items = new ArrayList<>();
        while (iterator.hasNext()) {
            Item item = iterator.next().get();
            items.add(item.objectName());
        }
        log.debug("当前包下的文件 '{}' '{}' ", packageName, items);
        //需要对文件进行排序
        if (!items.isEmpty() && !checkMergeFile(items)) {
            List<String> fileUrlList = items
                .stream()
                .sorted((Comparator.comparingInt(this::getNumber)))
                .collect(Collectors.toList());
            return fileUrlList;
        }
        return items;
    }

    /**
     * 判断是否是合并后的文件
     *
     * @param items 文件夹下所有文件
     * @return
     */
    private boolean checkMergeFile(List<String> items) {
        return items.get(items.size() - 1).contains(".");
    }

    private int getNumber(String fileUrl) {
        String[] splitString = fileUrl.split("-");
        return Integer.parseInt(splitString[1]);
    }

}