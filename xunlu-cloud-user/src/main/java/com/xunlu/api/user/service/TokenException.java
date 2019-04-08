package com.xunlu.api.user.service;

/**
 * @author liweibo
 */
public abstract class TokenException extends ServiceException {
    public TokenException() {
    }

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
