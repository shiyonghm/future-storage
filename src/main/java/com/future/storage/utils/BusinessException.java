package com.future.storage.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 业务异常
 *
 * @author shiyong
 * 2019-10-08 13:40
 */
@Data
@AllArgsConstructor
public final class BusinessException extends RuntimeException {

    /**
     * 异常信息
     */
    private String errorMsg;
}
