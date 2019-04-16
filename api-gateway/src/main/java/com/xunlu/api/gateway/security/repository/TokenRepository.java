package com.xunlu.api.gateway.security.repository;

import com.xunlu.api.user.domain.User;

/**
 * token数据库操作相关接口
 * @author liweibo
 */
public interface TokenRepository {
    /**
     * 通过token获取用户信息
     * @param token
     * @return 如果获取不到，返回null
     */
    User getUserByToken(String token);
}
