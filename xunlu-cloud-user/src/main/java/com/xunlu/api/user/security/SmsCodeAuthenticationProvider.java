package com.xunlu.api.user.security;

import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.service.UserService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.client.RestTemplate;

/**
 * 一个AuthenticationProvider实现, 认证手机号、验证码登录.
 * @author liweibo
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
    private UserService userService;
    private RestTemplate restTemplate;

    public SmsCodeAuthenticationProvider(UserService userService, RestTemplateBuilder builder) {
        this.userService = userService;
        restTemplate = builder.build();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        String phone = (String) authenticationToken.getPrincipal();
        User user = userService.findByPhone(phone);
        if (user == null) {
            throw new InternalAuthenticationServiceException("指定手机号[" + phone + "]获取不到用户信息");
        }

        String smsCode = (String) authenticationToken.getCredentials();
        if (smsCode == null) {
            throw new BadCredentialsException("手机验证码为空");
        }

        checkSmsCode(phone, smsCode);

        return null;
    }

    private void checkSmsCode(String phone, String smsCode) {

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationProvider.class.isAssignableFrom(authentication);
    }
}
