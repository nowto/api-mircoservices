package com.xunlu.api.user.repository;

import com.xunlu.api.user.domain.User;
import org.springframework.lang.Nullable;

/**
 * 与{@link com.xunlu.api.user.domain.AccessToken} 相关的数据库操作接口
 * @author liweibo
 */
public interface TokenRepository {
    /**
     * 获取用户{@code user}的token, 如果不存在返回null
     * @param user 要获取token的用户
     * @return token,
     */
    @Nullable String findOne(User user);

    /**
     * 将{@code user}的{@code token}存入数据库
     * @param user token属主
     * @param token token
     */
    void addToken(User user, String token);
}
