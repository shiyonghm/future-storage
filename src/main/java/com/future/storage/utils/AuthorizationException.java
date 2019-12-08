package com.future.storage.utils;

import lombok.Data;

/**
 * 访问身份异常
 *
 * @author shiyong
 * 2019-10-09 10:15
 */
@Data
public class AuthorizationException extends RuntimeException {

    /**
     * 异常信息
     */
    private String errorMsg = "认证失败，拒绝访问！";
}
