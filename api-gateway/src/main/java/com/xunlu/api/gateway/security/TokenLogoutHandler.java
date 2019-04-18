package com.xunlu.api.gateway.security;


import com.xunlu.api.gateway.security.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token注销处理器.
 * 用户退出后，需清除服务端存储的token.
 * @author liweibo
 */
public class TokenLogoutHandler implements LogoutHandler {
    private TokenService tokenService;

    private TokenObtainStrategy tokenObtainStrategy = new AuthorizationHeaderTokenObtainStrategy();

    public TokenLogoutHandler(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * requset参数必传
     * @param request 用于从中获取token， 不能为null
     * @param response 没有使用到，可以为null
     * @param authentication 没有使用到，可以为null
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Assert.notNull(request, "HttpServletRequest required");
        String token = tokenObtainStrategy.obtianToken(request);
        if (token == null) {
            return;
        }
        tokenService.invalidateToken(token);
    }
}
