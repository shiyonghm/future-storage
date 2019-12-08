package com.future.storage.utils;

import lombok.Data;

/**
 * 参数异常
 *
 * @author shiyong
 * 2019-10-09 15:03
 */
@Data
public final class ParameterException extends RuntimeException {

    /**
     * 异常信息
     */
    private String errorMsg = "请求参数错误！";
}
