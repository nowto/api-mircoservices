package com.xunlu.api.user.security;

import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.service.SmsService;
import com.xunlu.api.user.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

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
        SmsCredentials smsCredentials = (SmsCredentials) authenticationToken.getCredentials();

        if (smsCredentials == null) {
            throw new BadCredentialsException("手机验证码为空");
        }

        //check sms code
        String appKey = smsCredentials.getAppKey();
        String zone = smsCredentials.getZone();
        String phone = authentication.getName();
        String smsCode = smsCredentials.getCode();
        if (smsCode == null) {
            throw new BadCredentialsException("手机验证码为空");
        }
        checkSmsCode(appKey, zone, phone, smsCode);

        User user = userService.findByPhone(phone);
        if (user == null) {
            userService.addUser(User.newPhoneRegisterUser(phone, zone, smsCode));
            user = userService.findByPhone(phone);
        }
        if (user == null) {
            throw new InternalAuthenticationServiceException("指定手机号[" + phone + "]获取不到用户信息");
        }

        SmsCodeAuthenticationToken successToken = new SmsCodeAuthenticationToken(user, authentication.getCredentials(),
                AuthorityUtils.createAuthorityList("ROLE_USER"));
        successToken.setDetails(authentication.getDetails());

        return successToken;
    }

    private void checkSmsCode(String appKey, String zone, String phone, String smsCode) {
        try {
            boolean verifyRet = smsService.verify(appKey, zone, phone, smsCode);
            if (!verifyRet) {
                throw new BadCredentialsException("短信验证码错误");
            }
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
