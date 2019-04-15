package com.xunlu.api.gateway.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 一个{@link org.springframework.security.core.Authentication}的实现, 用于token的认证.
 * 与{@link TokenAuthenticationFilter}、{@link TokenAuthenticationProvider} 配套使用
 * @see TokenAuthenticationFilter
 * @see TokenAuthenticationProvider
 * @author liweibo
 */
public class TokenAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;
    private Object credentials;

    /**
     * 该构造器可以在任何位置使用,创建一个可以安全使用的<code>TokenAuthenticationToken</code>, 并且{@link #isAuthenticated()}
     * 会返回 <code>false</code>.
     */
    public TokenAuthenticationToken(String token) {
        super(null);
        this.principal = token;
        this.credentials = token;
        setAuthenticated(false);
    }

    /**
     * 该构造器应该仅仅是在<code>AuthenticationManager</code>或<code>AuthenticationProvider</code>中被使用,用于创建认证了的可信token
     */
    public TokenAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return getToken();
    }

    public String getToken() {
        return (this.getCredentials() == null) ? null : this.getCredentials().toString();
    }

}
