package com.xunlu.api.common.restful.exception;

import org.springframework.http.HttpStatus;

/**
 * 资源找不到异常
 * @author liweibo
 */
public class ResourceNotFoundServiceException extends ServiceException {
    {
        status = HttpStatus.NOT_FOUND;
    }
    public ResourceNotFoundServiceException(String message) {
        super(message);
    }
}
