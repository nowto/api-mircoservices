package com.xunlu.api.user.service;

import com.xunlu.api.user.domain.AccessToken;
import com.xunlu.api.user.domain.User;
import org.springframework.util.DigestUtils;

/**
 * TokenService抽象类,用于更方便的实现TokenService.
 * 主要封装了token生成的算法.
 * @author liweibo
 */
abstract class AbstractTokenService implements TokenService{
    protected static final String LOGIN_TOKEN_KEY = "tyui2345";

    protected AccessToken generateToken(User user) {
        if (user == null) {
            throw new TokenCreationException("用户不存在无法生成token");
        }
        Integer userId = user.getId();
        if (userId == null) {
            throw new TokenCreationException("用户id不存在,无法生成token");
        }
        String rawToken = LOGIN_TOKEN_KEY + userId + System.currentTimeMillis();
        return new AccessToken(DigestUtils.md5DigestAsHex(rawToken.getBytes()));
    }
}
