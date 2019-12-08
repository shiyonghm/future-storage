package com.future.storage.utils;

import java.util.UUID;

/**
 * UUID工具类
 *
 * @author shiyong
 * 2019-09-30 09:33
 */
public class UUIDUtils {

    /**
     * 生成32位UUID
     * @author shiyong
     * 2019-09-30 09:45
     * @return java.lang.String
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();

        return uuid.replaceAll("-", "");
    }
}
