package com.xunlu.api.common.restful.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * 服务层抛出的统一异常类。
 * 代表非框架、类库、数据库抛出的异常，而是我们自己的应用代码抛出的异常。
 * 构造该类及其子类异常时，应该尽量指定具体的HTTP响应码，如果没有指定，将使用{@link HttpStatus#INTERNAL_SERVER_ERROR 500}
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

    /**
     * 返回包含一个ResponseEntity方便反回前端.
     * 该ResponseEntity的status为该异常的status,
     * 该ResponseEntity的响应体为该异常对应的ApiError实现.
     * @return
     */
    public ResponseEntity<ServiceExceptionApiError> toApiResponseEntity() {
        return new ResponseEntity(new ServiceApiErrorImpl(), getStatus());
    }

    private class ServiceApiErrorImpl implements ServiceExceptionApiError {

        @Override
        public String getMessage() {
            return ServiceException.this.getMessage();
        }

        @Override
        public String getException() {
            return ServiceException.this.getClass().getName();
        }
    }
}
