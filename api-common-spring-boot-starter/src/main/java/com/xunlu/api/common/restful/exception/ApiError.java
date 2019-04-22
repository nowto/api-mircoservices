package com.xunlu.api.common.restful.exception;

import org.springframework.http.HttpStatus;
/**
 * 表示 restful api 返回前端的错误.
 * 程序发生的异常将转化为该接口实现的实例作为响应体返回前端.
 * @author liweibo
 */
@FunctionalInterface
public interface ApiError {
    /**
     * 非业务异常 api error.
     *
     * 表示 restful api 非业务异常 返回给前端的错误.
     * 所谓非业务异常, 即框架、类库、数据库dao等抛出的异常，不是我们的业务代码抛出的异常.
     *
     * 该类异常应该返回前端{@linkplain HttpStatus#INTERNAL_SERVER_ERROR 500}状态码异常，代表服务器内部错误.
     */
    ApiError NON_SERVICE_EXCEPTION_API_ERROR = () -> "内部服务器错误";

    /**
     * 未知参数错误 api error
     */
    ApiError UNKNOWN_PARMETER_ERROR_API_ERROR = () -> "未知参数错误";



    /**
     * 返回错误消息，该消息应该尽量使用户可读.
     * 以便于前端可以直接将该消息展示给用户, 尽量不要涉及技术.
     * @return
     */
    String getMessage();

}
