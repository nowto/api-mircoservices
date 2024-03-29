package com.xunlu.api.user.security;

import com.xunlu.api.user.domain.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 一个{@link org.springframework.security.core.Authentication}的实现, 用于手机号、验证码登录认证.
 * 与{@link SmsCodeAuthenticationFilter}、{@link SmsCodeAuthenticationProvider} 配套使用
 * @see SmsCodeAuthenticationFilter
 * @see SmsCodeAuthenticationProvider
 * @author liweibo
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;
    private Object credentials;

    /**
     * 该构造器可以在任何位置使用,创建一个可以安全使用的<code>SmsCodeAuthenticationToken</code>, 并且{@link #isAuthenticated()}
     * 会返回 <code>false</code>.
     */
    public SmsCodeAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    /**
     * 该构造器应该仅仅是在<code>AuthenticationManager</code>或<code>AuthenticationProvider</code>中被使用,用于创建认证了的可信token
     */
    public SmsCodeAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
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
     * 覆盖，使用自定义的{@link com.xunlu.api.user.domain.User}对象,获取手机号
     * @return
     */
    @Override
    public String getName() {
        if (this.getPrincipal() instanceof User) {
            return ((User) this.getPrincipal()).getPhone();
        }
        return (this.getPrincipal() == null) ? "" : this.getPrincipal().toString();
    }
}
