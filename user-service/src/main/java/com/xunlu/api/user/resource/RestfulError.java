package com.xunlu.api.user.resource;

/**
 * 表示 restful api 返回前端的错误
 * @author liweibo
 */
public class RestfulError {
    /**
     * 错误消息
     */
    private String error;

    public RestfulError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
