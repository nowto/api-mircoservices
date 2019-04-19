package com.xunlu.api.common.restful.exception;

/**
 * 表示 restful api 业务异常 返回给前端的错误
 */
public interface ServiceExceptionApiError extends ApiError {
    /**
     * 返回业务异常的 全限定类名.
     * 使用全限定类名细化http状态码,便于前端开发者进行程序处理.
     * @return 异常类的全限定类名
     */
    String getException();
}
