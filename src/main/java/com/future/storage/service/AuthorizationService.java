package com.future.storage.service;

/**
 * 认证授权Service
 *
 * @author shiyong
 * 2019-09-29 16:21
 */
public interface AuthorizationService {

    /**
     * 进行认证授权
     * @author shiyong
     * 2019-10-09 14:42
     * @param authorization 认证信息
     * @return boolean
     */
    boolean doAuthorization(String authorization);
}
