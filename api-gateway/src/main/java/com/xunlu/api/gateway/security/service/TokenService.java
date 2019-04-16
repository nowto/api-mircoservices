package com.xunlu.api.gateway.security.service;

import com.xunlu.api.user.domain.User;

/**
 * token服务类
 * @author liweibo
 */
public interface TokenService {

    /**
     * 根据token获取用户信息
     * @param token token
     * @return 用户， 如果获取不到将返回null
     */
    User getUser(String token);
}
