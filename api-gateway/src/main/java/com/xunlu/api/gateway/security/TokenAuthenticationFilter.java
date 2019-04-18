package com.xunlu.api.gateway.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理token认证过滤器.
 * 默认请求处理的URL{@code /token}, 仅支持POST请求.
 * 与{@link TokenAuthenticationProvider}、{@link TokenAuthenticationToken} 配套使用
 * @see TokenAuthenticationProvider
 * @see TokenAuthenticationToken
 * @author liweibo
 */
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    {
        //认证成功后继续执行请求
        setContinueChainBeforeSuccessfulAuthentication(true);
    }

    private TokenObtainStrategy tokenObtainStrategy = new AuthorizationHeaderTokenObtainStrategy();

    public TokenAuthenticationFilter(String userServiceAuthenticatinUrl) {
        super(new NegatedRequestMatcher(new AntPathRequestMatcher(userServiceAuthenticatinUrl)));
    }

    /**
     * 当token不为null时，说明前端明确需要使用token认证.
     * 而如果token为null，将执行doFilter交给后续AuthenticationFilter(AnonymousAuthenticationFilter), 这样就实际上允许没有token的请
     * 求也能得到认证，认证为AnonymousUser，这就相当于允许一些请求即便没有登录也能被访问
     * @param request
     * @param response
     * @return
     * @see super#requiresAuthentication(HttpServletRequest, HttpServletResponse)
     */
    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String token = tokenObtainStrategy.obtianToken(request);
        return super.requiresAuthentication(request, response) && token != null;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String token = tokenObtainStrategy.obtianToken(request);
        TokenAuthenticationToken authRequest = new TokenAuthenticationToken(token);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void setDetails(HttpServletRequest request, TokenAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
