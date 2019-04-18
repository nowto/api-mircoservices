package com.xunlu.api.user.service;

import org.springframework.http.HttpStatus;

/**
 * Token创建发生了异常
 * @author liweibo
 */
public class TokenCreationException extends TokenException {
    {
        status = HttpStatus.UNAUTHORIZED;
    }

    public TokenCreationException(String message) {
        super(message);
    }
}
