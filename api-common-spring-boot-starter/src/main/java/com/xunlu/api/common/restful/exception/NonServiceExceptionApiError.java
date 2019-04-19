package com.xunlu.api.common.restful.exception;

import org.springframework.http.HttpStatus;
/**
 * 表示 restful api 非业务异常 返回给前端的错误.
 * 所谓非业务异常, 即框架、类库、数据库dao等抛出的异常，不是我们的业务代码抛出的异常.
 *
 * 该类异常应该返回前端{@linkplain HttpStatus#INTERNAL_SERVER_ERROR 500}状态码异常，代表服务器内部错误.
 *
 * 因为该类错误不会有变化的数据，使用单例实现
 * @author liweibo
 */
public class NonServiceExceptionApiError implements ApiError {
    public static final NonServiceExceptionApiError INSTANCE = new NonServiceExceptionApiError();

    private NonServiceExceptionApiError() {
        //单例，禁止外部实例化
    }

    public static final String MESSAGE = "内部服务器错误";

    /**
     * 服务器内部错误不易暴露太多信息给客户端，使用常量{@link #MESSAGE}返回错误消息
     */
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
