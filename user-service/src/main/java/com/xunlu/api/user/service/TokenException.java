package com.xunlu.api.user.service;

/**
 * @author liweibo
 */
public abstract class TokenException extends ServiceException {

    public TokenException(String message) {
        super(message);
    }
}
