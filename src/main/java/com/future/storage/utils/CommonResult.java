package com.future.storage.utils;

import lombok.Data;

/**
 * 通用返回信息类
 *
 * @author shiyong
 * 2019-10-08 10:46
 */
@Data
public final class CommonResult<T> {

    public static final String OK = "0";// 成功
    public static final String FAIL = "1";// 失败

    /**
     * 结果代码（0：成功，1：失败）
     */
    private String resultCode = "";

    /**
     * 结果信息
     */
    private String resultMsg = "";

    /**
     * 结果数据
     */
    private T resultData;

    public CommonResult() {

    }

    public CommonResult(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public CommonResult(String resultMsg) {
        this.resultCode = CommonResult.OK;
        this.resultMsg = resultMsg;
    }

    public CommonResult(T resultData) {
        this.resultCode = CommonResult.OK;
        this.resultMsg = "成功！";
        this.resultData = resultData;
    }

}
