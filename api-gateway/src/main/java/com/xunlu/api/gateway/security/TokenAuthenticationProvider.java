package com.xunlu.api.gateway.security;

import com.xunlu.api.user.domain.User;
import com.xunlu.api.gateway.security.service.TokenService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * 一个AuthenticationProvider实现, 认证token
 * 与{@link TokenAuthenticationFilter}、{@link TokenAuthenticationToken} 配套使用
 * @see TokenAuthenticationFilter
 * @see TokenAuthenticationToken
 * @author liweibo
 */
public class TokenAuthenticationProvider implements AuthenticationProvider {
    private TokenService tokenService;

    public TokenAuthenticationProvider(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TokenAuthenticationToken authenticationToken = (TokenAuthenticationToken) authentication;
        String token = authenticationToken.getToken();

        if (token == null) {
            throw new InternalAuthenticationServiceException("获取不到token");
        }

        //check token
        User user = checkUser(token);

        TokenAuthenticationToken successToken = new TokenAuthenticationToken(user, authentication.getCredentials(),
                AuthorityUtils.createAuthorityList("ROLE_USER"));
        successToken.setDetails(authentication.getDetails());

        return successToken;
    }


    private User checkUser(String token) {
        User user = tokenService.getUser(token);
        if (user == null) {
            throw new InternalAuthenticationServiceException("指定token获取不到用户");
        }
        return user;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
