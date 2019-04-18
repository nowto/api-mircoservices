package com.xunlu.api.gateway.security;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

/**
 * 从请求头{@link HttpHeaders#AUTHORIZATION}获取token的{@link TokenObtainStrategy}实现
 */
class AuthorizationHeaderTokenObtainStrategy implements TokenObtainStrategy {
    @Override
    public String obtianToken(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }
}
