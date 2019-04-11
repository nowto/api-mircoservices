package com.xunlu.api.user.security;

import com.xunlu.api.user.domain.ThirdUser;
import com.xunlu.api.user.infrastructure.CodeEnumUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.HttpMethod;
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
 * 默认请求处理的URL{@code /token}
 * 与{@link ThirdUserAuthenticationProvider}、{@link ThirdUserAuthenticationToken} 配套使用
 *
 * @author liweibo
 * @see ThirdUserAuthenticationProvider
 * @see ThirdUserAuthenticationToken
 */
public class ThirdUserAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    /**
     * credentials
     */
    public static final String SECURITY_TYPE = "type";
    public static final String SECURITY_SIGNATURE = "signature";
    public static final String SECURITY_KEY = "key";

    /**
     * principal
     */
    public static final String SECURITY_OPENID = "openid";
    public static final String SECURITY_USERNAME = "user_name";
    public static final String SECURITY_IMGURL = "img_url";

    public ThirdUserAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    public ThirdUserAuthenticationFilter() {
        super(new AntPathRequestMatcher("/token", HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        ThirdUserPrincipal principal = obtainPrincipal(request);
        ThirdUserCredentials credentials = obtainCredentials(request);

        ThirdUserAuthenticationToken authRequest = new ThirdUserAuthenticationToken(principal, credentials);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void setDetails(HttpServletRequest request, ThirdUserAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    private ThirdUserPrincipal obtainPrincipal(HttpServletRequest request) {
        String type = obtainRequestParameter(request, SECURITY_TYPE);
        ThirdUser.Type loginType = NumberUtils.isParsable(type) ?
                CodeEnumUtil.codeOf(ThirdUser.Type.class, Integer.valueOf(type), ThirdUser.Type.WEIXIN) : ThirdUser.Type.WEIXIN;

        String openid = obtainRequestParameter(request, SECURITY_OPENID);
        openid = StringUtils.defaultString(openid, "");
        openid = openid.trim();

        String userName = obtainRequestParameter(request, SECURITY_USERNAME);
        String imgUrl = obtainRequestParameter(request, SECURITY_IMGURL);

        return new ThirdUserPrincipal(loginType, openid, userName, imgUrl);
    }

    private ThirdUserCredentials obtainCredentials(HttpServletRequest request) {
        String signature = obtainRequestParameter(request, SECURITY_SIGNATURE);
        String key = obtainRequestParameter(request, SECURITY_KEY);

        ThirdUserCredentials credentials = new ThirdUserCredentials(signature);
        credentials.setSignatureKey(key);
        return credentials;
    }

    private String obtainRequestParameter(HttpServletRequest request, String securityType) {
        String value = request.getParameter(securityType);
        return value;
    }
}
