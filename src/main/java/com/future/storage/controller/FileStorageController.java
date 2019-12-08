package com.future.storage.controller;

import com.future.storage.service.FileStorageService;
import com.future.storage.utils.CommonResult;
import com.future.storage.utils.ParameterException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件存储控制器
 *
 * @author shiyong
 * 2019-09-29 17:48
 */
@RestController
@RequestMapping("/file")
public class FileStorageController {

    @Resource
    private FileStorageService fileStorageService;

    /**
     * 上传文件
     * @param file 文件
     * @param request 请求
     * @return java.lang.String
     * @author shiyong
     * 2019/10/9 16:46
     */
    @PostMapping("/upload")
    public CommonResult<String> uploadFile(MultipartFile file, HttpServletRequest request) {

        if (null == file) {
            throw new ParameterException();
        }

        String accessId = getAccessIdFromRequest(request);

        fileStorageService.uploadFile(accessId, file);

        return new CommonResult<>("上传文件成功！");
    }

    /**
     * 下载文件
     * @param fileName 文件名称
     * @param request 请求
     * @param response 响应
     * @author shiyong
     * 2019/10/9 16:47
     */
    @PostMapping("/download")
    public void downloadFile(String fileName, HttpServletRequest request, HttpServletResponse response) {

        String accessId = getAccessIdFromRequest(request);

        if (null == fileName || "".equals(fileName)) {
            throw new ParameterException();
        }

        fileStorageService.downloadFile(accessId, fileName, response);
    }

    /**
     * 删除文件
     * @param fileName 文件名称
     * @param request 请求
     * @return java.lang.String
     * @author shiyong
     * 2019/10/9 16:49
     */
    @PostMapping("/delete")
    public CommonResult<String> deleteFile(String fileName, HttpServletRequest request) {
        String accessId = getAccessIdFromRequest(request);

        if (null == fileName || "".equals(fileName)) {
            throw new ParameterException();
        }

        fileStorageService.deleteFile(accessId, fileName);

        return new CommonResult<>("删除文件成功！");
    }

    /**
     * 从request中获取访问ID
     * @author shiyong
     * 2019-09-30 14:24
     * @param request HttpServletRequest对象
     * @return java.lang.String
     */
    private String getAccessIdFromRequest(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (null == authorization || "".equals(authorization)) {
            throw new ParameterException();
        }

        return authorization.split(":")[0];
    }
}
