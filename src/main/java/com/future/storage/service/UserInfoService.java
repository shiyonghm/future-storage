package com.future.storage.service;

/**
 * 用户信息Service
 *
 * @author shiyong
 * 2019-09-29 16:21
 */
public interface UserInfoService {
    /**
     * 保存用户信息
     * @author shiyong
     * 2019-09-30 09:08
     * @param name 用户名称
     */
    void saveUserInfo(String name);

    /**
     * 查询用户accessKey
     * @author shiyong
     * 2019-09-30 09:10
     * @param accessId 访问ID
     * @return java.lang.String
     */
    String getAccessKey(String accessId);


}
