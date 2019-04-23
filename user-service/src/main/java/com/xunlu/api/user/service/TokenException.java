package com.xunlu.api.user.service;

import com.xunlu.api.common.restful.exception.ServiceException;

/**
 * @author liweibo
 */
public abstract class TokenException extends ServiceException {

    public TokenException(String message) {
        super(message);
    }
}
