package com.xunlu.api.user.service;

import com.xunlu.api.user.domain.AccessToken;
import com.xunlu.api.user.domain.User;

/**
 * {@link com.xunlu.api.user.domain.AccessToken}服务类
 * @author liweibo
 */
public interface TokenService {
    AccessToken generateToken(User user);

}
