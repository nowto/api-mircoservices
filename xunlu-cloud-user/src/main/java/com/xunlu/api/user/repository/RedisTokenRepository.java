package com.xunlu.api.user.repository;

import com.xunlu.api.user.domain.AccessToken;
import com.xunlu.api.user.domain.User;

public class RedisTokenRepository implements TokenRepository {

    @Override
    public AccessToken findOne(User user) {
        return null;
    }

    @Override
    public void addToken(User user, AccessToken token) {

    }
}
