package com.xunlu.api.gateway.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理短信验证码认证过滤器.
 * 默认请求处理的URL{@code /token}, 仅支持POST请求.
 * 与{@link TokenAuthenticationProvider}、{@link TokenAuthenticationToken} 配套使用
 * @see TokenAuthenticationProvider
 * @see TokenAuthenticationToken
 * @author liweibo
 */
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    
    public TokenAuthenticationFilter(String userServiceAuthenticatinUrl) {
        super(new NegatedRequestMatcher(new AntPathRequestMatcher(userServiceAuthenticatinUrl)));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!HttpMethod.POST.matches(request.getMethod())) {
            throw new AuthenticationServiceException("认证不支持请求方法: " + request.getMethod());
        }
        String token = obtainToken(request);
        TokenAuthenticationToken authRequest = new TokenAuthenticationToken(token);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void setDetails(HttpServletRequest request, TokenAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    private String obtainToken(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }
}
