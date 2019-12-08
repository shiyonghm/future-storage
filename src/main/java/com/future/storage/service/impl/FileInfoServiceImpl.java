package com.future.storage.service.impl;

import com.future.storage.dao.FileInfoRepository;
import com.future.storage.domain.FileInfo;
import com.future.storage.service.FileInfoService;
import com.future.storage.utils.DateUtils;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * 文件信息Service
 *
 * @author shiyong
 * 2019-09-29 17:37
 */
@Service
public class FileInfoServiceImpl implements FileInfoService {

    @Reference
    private FileInfoRepository fileInfoRepository;

    /**
     * 保存文件信息
     *
     * @param accessId 访问ID
     * @param fileName 文件名称
     * @param filePath 文件路径
     * @author shiyong
     * 2019-09-30 10:10
     */
    @Override
    public void saveFileInfo(String accessId, String fileName, String filePath) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(fileName);
        fileInfo.setFilePath(filePath);
        fileInfo.setAccessId(accessId);
        fileInfo.setCreateTime(DateUtils.getTime());

        fileInfoRepository.save(fileInfo);
    }

    /**
     * 根据文件名称获取文件路径
     *
     * @param accessId 访问ID
     * @param fileName 文件名称
     * @return java.lang.String
     * @author shiyong
     * 2019-09-30 10:20
     */
    @Override
    public String getFilePath(String accessId, String fileName) {
        FileInfo fileInfo = fileInfoRepository.getFileInfoByAccessIdAndFileName(accessId, fileName);

        if (null == fileInfo) {
            return "";
        }

        return fileInfo.getFilePath();
    }

    /**
     * 删除文件信息
     *
     * @param accessId 访问ID
     * @param fileName 文件名称
     * @author shiyong
     * 2019-09-30 10:23
     */
    @Override
    public void deleteFileInfo(String accessId, String fileName) {
        fileInfoRepository.deleteByAccessIdAndFileName(accessId, fileName);
    }
}
