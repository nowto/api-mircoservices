package com.xunlu.api.gateway.security;

import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;

/**
 * 从{@code request}获取token的策略接口.
 */
interface TokenObtainStrategy {
    /**
     * 从请求中获取前端传递的token.
     * @param request
     * @return token, 当token不存在时返回null
     */
    @Nullable String obtianToken(HttpServletRequest request);
}
