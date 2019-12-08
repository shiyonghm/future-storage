package com.future.storage.utils;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * FastDFS工具类
 *
 * @author shiyong
 * 2019-09-29 10:39
 */
@Component
@Slf4j
public class FastDFSUtils {
    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * 上传文件
     * @author shiyong
     * 2019-09-29 11:10
     * @param file 文件
     * @return java.lang.String
     * @throws IOException
     */
    public String uploadFile(File file) throws IOException {
        //文件名
        String fileName = file.getName();

        // 文件扩展名
        String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);

        // 文件流
        InputStream in = new FileInputStream(file);

        StorePath storePath = storageClient.uploadFile(in, file.length(), fileExtName, null);

        return storePath.getFullPath();
    }

    /**
     * 上传文件
     * @author shiyong
     * 2019-09-30 15:01
     * @param file 文件
     * @return java.lang.String
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IOException {

        //文件名
        String fileName = file.getOriginalFilename();

        // 文件扩展名
        String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);

        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), fileExtName, null);

        return storePath.getFullPath();
    }

    /**
     * 下载文件
     * @author shiyong
     * 2019-09-29 14:16
     * @param filePath 文件路径(groupName/path)
     * @return java.io.InputStream
     */
    public InputStream downloadFile(String filePath) {

        StorePath storePath = StorePath.parseFromUrl(filePath);

        // 获取文件
        byte[] bytes = storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());

        InputStream ins = new ByteArrayInputStream(bytes);

        return ins;
    }

    /**
     * 删除文件
     * @author shiyong
     * 2019-09-29 14:18
     * @param filePath 文件路径(groupName/path)
     */
    public void deleteFile(String filePath) {

        storageClient.deleteFile(filePath);

    }
}
