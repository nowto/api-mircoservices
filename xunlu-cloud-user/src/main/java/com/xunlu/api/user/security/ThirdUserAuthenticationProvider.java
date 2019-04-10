package com.xunlu.api.user.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * 一个AuthenticationProvider实现, 认证第三方帐号登录, 例如微信登录.
 * 与{@link ThirdUserAuthenticationFilter}、{@link ThirdUserAuthenticationToken} 配套使用
 * @see ThirdUserAuthenticationFilter
 * @see ThirdUserAuthenticationToken
 * @author liweibo
 */
public class ThirdUserAuthenticationProvider  implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ThirdUserAuthenticationToken authenticationToken = (ThirdUserAuthenticationToken) authentication;


        ThirdUserAuthenticationToken successToken = new ThirdUserAuthenticationToken(null, authentication.getCredentials(),
                AuthorityUtils.createAuthorityList("ROLE_USER"));
        successToken.setDetails(authentication.getDetails());
        return successToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ThirdUserAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
