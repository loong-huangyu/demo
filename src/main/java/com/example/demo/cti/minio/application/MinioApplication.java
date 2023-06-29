package com.example.demo.cti.minio.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.cti.minio.domain.MergeFile;
import com.example.demo.cti.minio.domain.UploadFile;
import com.example.demo.cti.minio.infrastructures.client.MinioClientWrapper;

/**
 * @author rain
 * @description:
 * @date 2023/1/3 4:59 下午
 */
@Service
public class MinioApplication {

    private final MinioClientWrapper minioClientWrapper;

    public MinioApplication(MinioClientWrapper minioClientWrapper) {
        this.minioClientWrapper = minioClientWrapper;
    }

    public void upload(UploadFile uploadFile) {
        minioClientWrapper.uploadFile(uploadFile);
    }

    public String merge(MergeFile mergeFile) {
        return minioClientWrapper.fileMerge(mergeFile);
    }

    public List<String> findByFileHash(String fileHash) throws Exception {
        return minioClientWrapper.getFolderList(fileHash);
    }

}
