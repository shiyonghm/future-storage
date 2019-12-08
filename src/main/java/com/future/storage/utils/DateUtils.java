package com.future.storage.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日期工具类
 *
 * @author shiyong
 * 2019-09-30 09:25
 */
public class DateUtils {

    /**
     * 获取当前时间
     * @author shiyong
     * 2019-09-30 09:28
     * @return java.lang.String
     */
    public static String getTime() {
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(cal.getTime());
    }

    /**
     * 获取时间戳
     * @author shiyong
     * 2019-10-09 13:36
     * @return long
     */
    public static long getTimeStamp() {
        return System.currentTimeMillis();
    }
}
