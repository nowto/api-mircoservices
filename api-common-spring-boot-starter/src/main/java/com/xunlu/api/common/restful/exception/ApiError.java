package com.xunlu.api.common.restful.exception;

/**
 * 表示 restful api 返回前端的错误.
 * 程序发生的异常将转化为该接口实现的实例作为响应体返回前端.
 * @author liweibo
 */
@FunctionalInterface
public interface ApiError {
    /**
     * 非业务异常 api error.
     * {@link NonServiceExceptionApiError#MESSAGE}
     */
    ApiError NON_SERVICE_EXCEPTION_API_ERROR = NonServiceExceptionApiError.INSTANCE;

    /**
     * 未知参数错误 api error
     */
    ApiError UNKNOWN_PARMETER_ERROR_API_ERROR = new ApiError() {
        @Override
        public String getMessage() {
            return "未知参数错误";
        }
    };



    /**
     * 返回错误消息，该消息应该尽量使用户可读.
     * 以便于前端可以直接将该消息展示给用户, 尽量不要涉及技术.
     * @return
     */
    String getMessage();

}
