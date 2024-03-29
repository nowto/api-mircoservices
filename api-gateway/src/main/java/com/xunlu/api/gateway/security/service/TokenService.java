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

    /**
     * 注销用户登录
     * @param id 用户id
     */
    void invalidateTokenForUser(Integer id);

    /**
     * 置{@code token}为无效
     * @param token token
     */
    void invalidateToken(String token);
}
