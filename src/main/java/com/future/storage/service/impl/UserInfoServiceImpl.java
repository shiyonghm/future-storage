package com.future.storage.service.impl;

import com.future.storage.dao.UserInfoRepository;
import com.future.storage.domain.UserInfo;
import com.future.storage.service.UserInfoService;
import com.future.storage.utils.BusinessException;
import com.future.storage.utils.DateUtils;
import com.future.storage.utils.UUIDUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户信息Service
 *
 * @author shiyong
 * 2019-09-29 17:34
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoRepository userInfoRepository;

    /**
     * 保存用户信息
     *
     * @param name 用户名称
     * @author shiyong
     * 2019-09-30 09:08
     */
    @Override
    public void saveUserInfo(String name) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setAccessId(UUIDUtils.getUUID());
        userInfo.setAccessKey(UUIDUtils.getUUID());
        userInfo.setCreateTime(DateUtils.getTime());

        userInfoRepository.insert(userInfo);
    }

    /**
     * 查询用户accessKey
     *
     * @param accessId 访问ID
     * @return java.lang.String
     * @author shiyong
     * 2019-09-30 09:10
     */
    @Override
    public String getAccessKey(String accessId) {
        UserInfo userInfo = userInfoRepository.findByAccessId(accessId);

        if (null == userInfo) {
            throw new BusinessException("");
        }

        return userInfo.getAccessKey();
    }
}
