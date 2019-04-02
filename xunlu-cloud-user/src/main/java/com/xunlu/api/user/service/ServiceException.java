package com.xunlu.api.user.service;

/**
 * 服务层抛出的统一异常类
 * @author liweibo
 */
public class ServiceException extends RuntimeException {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
