package com.future.storage.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户信息
 *
 * @author shiyong
 * 2019-09-29 16:21
 */
@Document(collection = "user_info")
@Setter
@Getter
public class UserInfo {

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 访问ID，用于区分用户
     */
    private String accessId;

    /**
     * 访问钥匙，用于生成签名字符串
     */
    private String accessKey;

    /**
     * 创建时间
     */
    private String createTime;

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", accessId='" + accessId + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
