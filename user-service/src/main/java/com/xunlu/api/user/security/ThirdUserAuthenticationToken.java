package com.xunlu.api.user.security;

import com.xunlu.api.user.domain.ThirdUser;
import com.xunlu.api.user.domain.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 一个{@link org.springframework.security.core.Authentication}的实现, 用于第三方帐号登录.
 * 例如微信登录
 * 与{@link ThirdUserAuthenticationFilter}、{@link ThirdUserAuthenticationProvider} 配套使用
 * @see ThirdUserAuthenticationFilter
 * @see ThirdUserAuthenticationProvider
 * @author liweibo
 */
public class ThirdUserAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;
    private Object credentials;

    /**
     * 该构造器可以在任何位置使用,创建一个可以安全使用的<code>ThirdUserAuthenticationToken</code>, 并且{@link #isAuthenticated()}
     * 会返回 <code>false</code>.
     */
    public ThirdUserAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    /**
     * 该构造器应该仅仅是在<code>AuthenticationManager</code>或<code>AuthenticationProvider</code>中被使用,用于创建认证了的可信token
     */
    public ThirdUserAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return credentials;
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

    public ThirdUser.Type getLoginType() {
        if (this.getPrincipal() instanceof ThirdUser) {
            return ((ThirdUser) this.getPrincipal()).getType();
        }
        if (this.getPrincipal() instanceof ThirdUserPrincipal) {
            return ((ThirdUserPrincipal) this.getPrincipal()).getType();
        }
        return ThirdUser.Type.WEIXIN;
    }

    /**
     * 返回第三方用户平台id.
     * 该平台该用户将只具有这个id， 不会出现多个平台id对应同一用户的情况
     * @return
     */
    public String getPlatformId() {
        if (this.getPrincipal() instanceof ThirdUser) {
            return ((ThirdUser) this.getPrincipal()).getOpenid();
        }
        if (this.getPrincipal() instanceof ThirdUserPrincipal) {
            return ((ThirdUserPrincipal) this.getPrincipal()).getPlatformId();
        }
        return (this.getPrincipal() == null) ? "" : this.getPrincipal().toString();
    }

    /**
     * 返回openid.
     * @return
     */
    public String getOpenid() {
        if (this.getPrincipal() instanceof ThirdUser) {
            return ((ThirdUser) this.getPrincipal()).getOpenid();
        }
        if (this.getPrincipal() instanceof ThirdUserPrincipal) {
            return ((ThirdUserPrincipal) this.getPrincipal()).getOpenid();
        }
        return (this.getPrincipal() == null) ? "" : this.getPrincipal().toString();
    }
}
