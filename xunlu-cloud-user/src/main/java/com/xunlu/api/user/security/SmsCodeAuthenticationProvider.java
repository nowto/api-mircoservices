package com.xunlu.api.user.security;

import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.service.SmsService;
import com.xunlu.api.user.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 一个AuthenticationProvider实现, 认证手机号、验证码登录.
 * @author liweibo
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
    private UserService userService;
    private SmsService smsService;

    public SmsCodeAuthenticationProvider(UserService userService, SmsService smsService) {
        this.userService = userService;
        this.smsService = smsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        Principal principal = (Principal) authenticationToken.getPrincipal();
        String phone = principal.getPhone();

        User user = userService.findByPhone(phone);
        if (user == null) {
            throw new InternalAuthenticationServiceException("指定手机号[" + phone + "]获取不到用户信息");
        }
        String smsCode = (String) authenticationToken.getCredentials();
        if (smsCode == null) {
            throw new BadCredentialsException("手机验证码为空");
        }

        String zone = principal.getZone();
        checkSmsCode(zone, phone, smsCode);
        return new SmsCodeAuthenticationToken(principal, authentication.getCredentials(), null);
    }

    private void checkSmsCode(String zone, String phone, String smsCode) {
        try {
            smsService.verify(null, zone, phone, smsCode);
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
