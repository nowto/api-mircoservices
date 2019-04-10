package com.xunlu.api.user.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理第三方登录认证过滤器.例如 微信登录
 * 请求处理的URL{@code /token}, 仅支持POST请求.
 * 与{@link ThirdUserAuthenticationProvider}、{@link ThirdUserAuthenticationToken} 配套使用
 * @see ThirdUserAuthenticationProvider
 * @see ThirdUserAuthenticationToken
 * @author liweibo
 */
public class ThirdUserAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public ThirdUserAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    public ThirdUserAuthenticationFilter() {
        super(new AntPathRequestMatcher("/token", HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        /*
        type=1

        signature=3a14723385646910f831c26b634b98bd
        key=myreindeer

        openid=null
        access_token=null
        user_name=null
        img_url=null
        unionid=null
         */
        if (!HttpMethod.POST.matches(request.getMethod())) {
            throw new AuthenticationServiceException("认证不支持请求方法: " + request.getMethod());
        }

        ThirdUserAuthenticationToken authRequest = new ThirdUserAuthenticationToken(null, null);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void setDetails(HttpServletRequest request, ThirdUserAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
