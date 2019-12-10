package com.future.storage.dao;

import com.future.storage.domain.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户信息DAO
 *
 * @author shiyong
 * 2019-09-29 16:21
 */
@Repository
public interface UserInfoRepository extends MongoRepository<UserInfo, String> {

    /**
     * 根据访问ID查询用户信息
     * @author shiyong
     * 2019-09-30 09:55
     * @param accessId 访问ID
     * @return com.future.storage.domain.UserInfo
     */
    UserInfo findByAccessId(String accessId);
}
