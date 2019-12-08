package com.future.storage.service;

/**
 * 文件信息Service
 *
 * @author shiyong
 * 2019-09-29 16:21
 */
public interface FileInfoService {

    /**
     * 保存文件信息
     * @author shiyong
     * 2019-09-30 10:10
     * @param accessId 访问ID
     * @param fileName 文件名称
     * @param filePath 文件路径
     */
    void saveFileInfo(String accessId, String fileName, String filePath);

    /**
     * 根据文件名称获取文件路径
     * @author shiyong
     * 2019-09-30 10:20
     * @param accessId 访问ID
     * @param fileName 文件名称
     * @return java.lang.String
     */
    String getFilePath(String accessId, String fileName);

    /**
     * 删除文件信息
     * @author shiyong
     * 2019-09-30 10:23
     * @param accessId 访问ID
     * @param fileName 文件名称
     */
    void deleteFileInfo(String accessId, String fileName);
}
