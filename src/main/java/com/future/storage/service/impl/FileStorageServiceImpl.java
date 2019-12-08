package com.future.storage.service.impl;

import com.future.storage.service.FileInfoService;
import com.future.storage.service.FileStorageService;
import com.future.storage.service.UserInfoService;
import com.future.storage.utils.BusinessException;
import com.future.storage.utils.DESUtils;
import com.future.storage.utils.FastDFSUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * 文件存储Service
 *
 * @author shiyong
 * 2019-09-29 17:38
 */
@Service
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    @Reference
    private UserInfoService userInfoService;

    @Reference
    private FileInfoService fileInfoService;

    @Reference
    private FastDFSUtils fastDFSUtils;

    @Reference
    private RedissonClient redissonClient;

    /**
     * 上传文件
     * @param accessId 访问ID
     * @param file 文件
     * @author shiyong
     * 2019/10/9 16:50
     */
    @Override
    public void uploadFile(String accessId, MultipartFile file) {

        // 保存文件信息
        String fileName = file.getOriginalFilename();

        // 上传文件到文件服务器
        String filePath;

        RLock lock = redissonClient.getLock("lock:storage:file:" + accessId + ":" + fileName);

        try {
            // 尝试加锁，最多等待60秒，上锁以后2小时自动解锁
            boolean lockFlag = lock.tryLock(60, 7200, TimeUnit.SECONDS);

            if (lockFlag) {
                // 判断文件是否已存在，如果已存在，则先删除，后上传
                filePath = fileInfoService.getFilePath(accessId, fileName);

                if (null != filePath && !"".equals(filePath)) {
                    // 删除文件信息
                    fileInfoService.deleteFileInfo(accessId, fileName);

                    // 删除文件
                    fastDFSUtils.deleteFile(filePath);
                }

                filePath = fastDFSUtils.uploadFile(file);

                fileInfoService.saveFileInfo(accessId, fileName, filePath);
            } else {
                throw new BusinessException("上传文件过于频繁，请稍后再试！");
            }

        } catch (Exception e) {
            log.error("上传文件发生异常！", e);

            throw new BusinessException("上传文件发生异常！");
        } finally {
            lock.unlock();
        }
    }

    /**
     * 下载文件
     * @param accessId 访问ID
     * @param fileName 文件名称
     * @param response 响应
     * @author shiyong
     * 2019/10/9 16:52
     */
    @Override
    public void downloadFile(String accessId, String fileName, HttpServletResponse response) {
        // 获取用户accessKey，用于解密fileName
        String accessKey = userInfoService.getAccessKey(accessId);

        try {
            fileName = DESUtils.decrypt(fileName, accessKey);
        } catch (Exception e) {
            log.error("下载文件时，解析文件名称发生异常！accessId=" + accessId + "，fileName=" + fileName, e);

            throw new BusinessException("解析文件名称发生异常！");
        }

        // 获取文件路径
        String filePath = fileInfoService.getFilePath(accessId, fileName);

        // 下载文件
        InputStream in = null;
        OutputStream out = null;

        try {
            in = fastDFSUtils.downloadFile(filePath);
            out = response.getOutputStream();

            byte[] b = new byte[512];

            int len;

            while((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }

            out.flush();
            in.close();
            out.close();
        } catch (IOException e) {
            log.error("获取文件发生异常！accessId=" + accessId + "，fileName=" + fileName, e);

            throw new BusinessException("获取文件发生异常！");
        } finally {
            try {
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                log.error("获取文件时，关闭输入流发生异常！accessId=" + accessId + "，fileName=" + fileName, e);
            }

            try {
                if(out != null){
                    out.close();
                }
            } catch (IOException e) {
                log.error("获取文件时，关闭输出流发生异常！accessId=" + accessId + "，fileName=" + fileName, e);
            }
        }

    }

    /**
     * 删除文件
     * @param accessId 访问ID
     * @param fileName 文件名称
     * @author shiyong
     * 2019/10/9 16:54
     */
    @Override
    public void deleteFile(String accessId, String fileName) {
        // 获取用户accessKey，用于解密fileName
        String accessKey = userInfoService.getAccessKey(accessId);

        try {
            fileName = DESUtils.decrypt(fileName, accessKey);
        } catch (Exception e) {
            log.error("删除文件时，解析文件名称发生异常！accessId=" + accessId + "，fileName=" + fileName, e);

            throw new BusinessException("解析文件名称发生异常！");
        }

        RLock lock = redissonClient.getLock("lock:storage:file:" + accessId + ":" + fileName);

        try {
            // 尝试加锁，最多等待60秒，上锁以后2小时自动解锁
            boolean lockFlag = lock.tryLock(60, 7200, TimeUnit.SECONDS);

            if (lockFlag) {
                // 获取文件路径
                String filePath = fileInfoService.getFilePath(accessId, fileName);

                if (null == filePath || "".equals(filePath)) {
                    log.error("删除文件时，文件不存在！accessId=" + accessId + "，fileName=" + fileName);

                    throw new BusinessException("文件不存在！");
                }

                // 删除文件信息
                fileInfoService.deleteFileInfo(accessId, fileName);

                // 删除文件
                fastDFSUtils.deleteFile(filePath);
            } else {
                throw new BusinessException("删除文件过于频繁，请稍后再试！");
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("删除文件发生异常！", e);

            throw new BusinessException("删除文件发生异常！");
        } finally {
            lock.unlock();
        }

    }
}
