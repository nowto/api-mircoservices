package com.xunlu.api.user.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理短信验证码认证过滤器.
 * 登录请求必须具有两个参数:手机号(phone)、验证码（smsCode）.
 * 请求处理的URL{@code /token}, 仅支持POST请求.
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SECURITY_PHONE = "phone";
    public static final String SECURITY_SMSCODE = "smsCode";
    public static final String HTTP_METHOD_POST = "HTTP_METHOD_POST";

    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher("/token", "HTTP_METHOD_POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!HTTP_METHOD_POST.equals(request.getMethod())) {
            throw new AuthenticationServiceException("认证不支持请求方法: " + request.getMethod());
        }

        String phone = obtainPhone(request);
        String smsCode = obtainSmsCode(request);
        if (phone == null) {
            phone = "";
        }
        if (smsCode == null) {
            smsCode = "";
        }
        phone = phone.trim();

        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(phone, smsCode);

        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 从请求中获取手机号
     * @param request 请求
     * @return 手机号
     */
    private String obtainPhone(HttpServletRequest request) {
        return request.getParameter(SECURITY_PHONE);
    }

    /**
     * 从请求中获取短信验证码
     * @param request 请求
     * @return 短信验证码
     */
    private String obtainSmsCode(HttpServletRequest request) {
        return request.getParameter(SECURITY_SMSCODE);
    }

    private void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }



}
