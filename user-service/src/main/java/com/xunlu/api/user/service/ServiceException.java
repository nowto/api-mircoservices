package com.xunlu.api.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * 服务层抛出的统一异常类
 * @author liweibo
 */
public class ServiceException extends RuntimeException {
    protected HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public ServiceException(HttpStatus status, String messge) {
        super(messge);
        Assert.notNull(status, "http状态码不能为null");
        this.status = status;
    }

    public ServiceException(String message) {
        super(message);
    }

    /**
     * 该异常应该以什么http状态码返回给客户端
     * @return 永远不会返回null
     */
    @NonNull public HttpStatus getStatus() {
        return status;
    }
}
