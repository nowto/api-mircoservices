package com.xunlu.api.common.restful.exception;

/**
 * 表示 restful api 返回前端的错误.
 * 程序发生的异常将转化为该接口实现的实例作为响应体返回前端.
 * @author liweibo
 */
public interface ApiError {
    /**
     * 返回错误消息，该消息应该尽量使用户可读.
     * 以便于前端可以直接将该消息展示给用户, 尽量不要涉及技术.
     * @return
     */
    String getMessage();
}
