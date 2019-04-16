package com.xunlu.api.common.restful;

/**
 * 表示 restful api 返回前端的错误
 * @author liweibo
 */
public class ApiError {
    /**
     * 错误消息
     */
    private String error;

    public ApiError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
