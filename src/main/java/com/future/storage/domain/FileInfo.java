package com.future.storage.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 文件信息
 *
 * @author shiyong
 * 2019-09-29 16:22
 */
@Document(collection = "file_info")
@Setter
@Getter
public class FileInfo {

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 文件名（原文件名）
     */
    private String fileName;

    /**
     * 文件路径（上传后文件的存储路径）
     */
    private String filePath;

    /**
     * 访问ID
     */
    private String accessId;

    /**
     * 创建时间
     */
    private String createTime;

    @Override
    public String toString() {
        return "FileInfo{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", accessId='" + accessId + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
