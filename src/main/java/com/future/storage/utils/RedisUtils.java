package com.future.storage.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * @author shiyong
 * 2019-09-27 14:00
 */
@Component
public class RedisUtils {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**  默认过期时长，单位：秒 */
    private final static long DEFAULT_EXPIRE = 60 * 60 * 24;

    /**  不设置过期时长 */
    private final static long NOT_EXPIRE = -1;

    /**
     * 保存数据（过期时间设置为一天）
     * @author shiyong
     * 2019-09-27 14:21
     * @param key 键
     * @param value 值
     */
    public void set(String key, Object value) {
        this.set(key, value, DEFAULT_EXPIRE);
    }

    /**
     * 保存数据
     * @author shiyong
     * 2019-09-27 14:25
     * @param key 键
     * @param value 值
     * @param timeout 数据过期时间
     */
    public void set(String key, Object value, long timeout) {
        if (NOT_EXPIRE == timeout) {
            // 不设置过期时长
            stringRedisTemplate.opsForValue().set(key, this.toJson(value));
        } else {
            stringRedisTemplate.opsForValue().set(key, this.toJson(value), timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 查询数据
     * @author shiyong
     * 2019-09-27 14:49
     * @param key 键
     * @return java.lang.String
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 查询数据对象
     * @author shiyong
     * 2019-09-27 14:53
     * @param key 键
     * @param clazz 类信息
     * @return T
     */
    public <T> T get(String key, Class<T> clazz) {
        String value = this.get(key);

        return null == value ? null : fromJson(value, clazz);
    }

    /**
     * 是否存在键
     * @author shiyong
     * 2019-09-27 14:56
     * @param key 键
     * @return boolean
     */
    public boolean hasKey(String key) {
        boolean result = false;

        try {
            result = stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            // 打印异常
        }

        return result;
    }

    /**
     * 删除数据
     * @author shiyong
     * 2019-09-27 15:02
     * @param key 键
     */
    public void delete(String key){
        stringRedisTemplate.delete(key);
    }

    /**
     * Object转成JSON字符串
     * @author shiyong
     * 2019-09-27 14:34
     * @param object 数据对象
     * @return java.lang.String
     */
    private String toJson(Object object) {
        if(object instanceof Integer || object instanceof Long || object instanceof Float
                || object instanceof Double || object instanceof Boolean || object instanceof String) {

            return String.valueOf(object);
        }

        return JSON.toJSONString(object);
    }

    /**
     * JSON字符串转成Object
     * @author shiyong
     * 2019-09-27 14:35
     * @param json JSON字符串
     * @param clazz 类信息
     * @return T
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }


}
