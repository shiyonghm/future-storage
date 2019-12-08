package com.future.storage.dao;


import com.future.storage.domain.FileInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 文件信息DAO
 *
 * @author shiyong
 * 2019-09-29 16:21
 */
@Repository
public interface FileInfoRepository extends MongoRepository<FileInfo, String> {

    /**
     * 根据访问ID和文件名称获取文件信息
     * @author shiyong
     * 2019-09-30 10:32
     * @param accessId 访问ID
     * @param fileName 文件名称
     * @return com.riskraider.storage.domain.FileInfo
     */
    FileInfo getFileInfoByAccessIdAndFileName(String accessId, String fileName);

    /**
     * 根据访问ID和文件名称删除文件信息
     * @author shiyong
     * 2019-09-30 10:32
     * @param accessId 访问ID
     * @param fileName 文件名称
     */
    void deleteByAccessIdAndFileName(String accessId, String fileName);
}
