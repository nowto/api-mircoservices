package com.xunlu.api.user.service;

/**
 * Token创建发生了异常
 * @author liweibo
 */
public class TokenCreationException extends TokenException {
    public TokenCreationException() {
    }

    public TokenCreationException(String message) {
        super(message);
    }

    public TokenCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
