package com.future.storage.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件存储Service
 *
 * @author shiyong
 * 2019-09-29 16:21
 */
public interface FileStorageService {

    /**
     * 上传文件
     * @param accessId 访问ID
     * @param file 文件
     * @author shiyong
     * 2019/10/9 16:50
     */
    void uploadFile(String accessId, MultipartFile file);

    /**
     * 下载文件
     * @param accessId 访问ID
     * @param fileName 文件名称
     * @param response 响应
     * @author shiyong
     * 2019/10/9 16:52
     */
    void downloadFile(String accessId, String fileName, HttpServletResponse response);

    /**
     * 删除文件
     * @param accessId 访问ID
     * @param fileName 文件名称
     * @author shiyong
     * 2019/10/9 16:54
     */
    void deleteFile(String accessId, String fileName);

}
