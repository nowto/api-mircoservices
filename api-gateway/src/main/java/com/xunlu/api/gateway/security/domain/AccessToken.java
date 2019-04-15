package com.xunlu.api.gateway.security.domain;

/**
 * 代表了前端访问需要认证以及需要权限的api时，请求需要携带token.
 * 每个token是一个字符串表示.
 *
 * 通常的做法:
 * 如果不携带正确的token访问需要认证权限的api，将返回401http状态码异常
 * 如果虽然携带的token正确，但该token不具有相关api的权限，将返回403http状态码异常
 * @author liweibo
 */
public class AccessToken {
    private String token;

    public AccessToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
