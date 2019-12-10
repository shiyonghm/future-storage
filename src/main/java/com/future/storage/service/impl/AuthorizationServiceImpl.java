package com.future.storage.service.impl;

import com.future.storage.service.AuthorizationService;
import com.future.storage.service.UserInfoService;
import com.future.storage.utils.AuthorizationException;
import com.future.storage.utils.DESUtils;
import com.future.storage.utils.DateUtils;
import com.future.storage.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 认证授权Service
 *
 * @author shiyong
 * 2019-10-09 14:37
 */
@Service("authorizationService")
@Slf4j
public class AuthorizationServiceImpl implements AuthorizationService {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 进行认证授权
     * 通过客户端认证信息验证客户端权限，客户签名信息由accessId、发送请求时的时间戳、accessKey拼接后进行MD5加密生成密文，
     * 再用发送请求时的时间戳和前面生成的字符串通过accessKey进行DES加密，得到的密文作为认证信息
     * @param authorization 认证信息
     * @return boolean
     * @author shiyong
     * 2019-10-09 14:42
     */
    @Override
    public boolean doAuthorization(String authorization) {

        String[] array = authorization.split(":");

        if (array.length < 2) {
            log.error("认证信息不全！authorization=" + authorization);

            throw new AuthorizationException();
        }

        // 得到accessId用于确认访问者
        String accessId = array[0];

        // 得到认证信息用于确认是否有访问权限
        String authInfo = array[1];

        // 根据accessId获取访问者accessKey
        String accessKey = userInfoService.getAccessKey(accessId);

        if (null == accessKey || "".equals(accessKey)) {
            log.error("未获取到用户accessKey，accessId=" + accessId);

            throw new AuthorizationException();
        }

        // 使用accessKey解密authInfo
        String plainText;

        try {
            plainText = DESUtils.decrypt(authInfo, accessKey);
        } catch (Exception e) {
            log.error("解密用户认证信息失败，authorization=" + authorization, e);

            throw new AuthorizationException();
        }

        // 从plainText中获取客户请求时间，请求时间为时间戳，时间戳为13位，精确到毫秒级
        // 请求时间加上时间间隔得到过期时间，间隔时间为30秒，超过30秒视为非法请求
        long requestTime;
        long expirationTime;

        try {
            long interval = 30000L;

            requestTime = Long.parseLong(plainText.substring(0, 13));
            expirationTime = requestTime + interval;
        } catch (Exception e) {
            log.error("解密用户请求过期时间失败，authorization=" + authorization, e);

            throw new AuthorizationException();
        }

        long now = DateUtils.getTimeStamp();

        // 比较当前时间是否过期
        if (now > expirationTime) {
            log.error("解密用户请求已过期，authorization=" + authorization);

            throw new AuthorizationException();
        }

        // 对accessKey加文件名称进行MD5加密，再跟客户提供的字符串进行比较
        try {
            String sign = MD5Utils.getMD5Code(accessId + requestTime + accessKey);

            if (!sign.equals(plainText.substring(13))) {
                log.error("校验用户认证信息失败，authorization=" + authorization);

                throw new AuthorizationException();
            }
        } catch (Exception e) {
            log.error("校验用户认证信息失败，authorization=" + authorization, e);

            throw new AuthorizationException();
        }

        return true;
    }
}
