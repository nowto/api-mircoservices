package com.xunlu.api.user.security;

import com.xunlu.api.user.domain.ThirdUser;
import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.DigestUtils;

/**
 * 一个AuthenticationProvider实现, 认证第三方帐号登录, 例如微信登录.
 * 与{@link ThirdUserAuthenticationFilter}、{@link ThirdUserAuthenticationToken} 配套使用
 * @see ThirdUserAuthenticationFilter
 * @see ThirdUserAuthenticationToken
 * @author liweibo
 */
public class ThirdUserAuthenticationProvider  implements AuthenticationProvider {

    /**
     * 签名加盐
     */
    public static final String SIGNATURE_SALT = "1@3$545";

    private UserService userService;

    public ThirdUserAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ThirdUserAuthenticationToken authenticationToken = (ThirdUserAuthenticationToken) authentication;
        ThirdUserCredentials credentials = (ThirdUserCredentials) authenticationToken.getCredentials();
        if (credentials == null) {
            throw new BadCredentialsException("签名信息不存在");
        }

        if (authenticationToken.getLoginType() == null) {
            throw new InternalAuthenticationServiceException("第三方帐户信息缺少登录类型");
        }
        if (StringUtils.isEmpty(authenticationToken.getOpenid())) {
            throw new InternalAuthenticationServiceException("第三方帐户信息缺少openid");
        }

        ThirdUser user = userService.findThirdUserByTypeAndOpenid(authenticationToken.getLoginType(), authenticationToken.getOpenid());
        if (user == null && authenticationToken.getPrincipal() instanceof ThirdUserPrincipal) {
            ThirdUserPrincipal principal = (ThirdUserPrincipal) authenticationToken.getPrincipal();
            userService.addUser(User.newThirdRegisterUser(principal.getType(), principal.getOpenid(), principal.getUserName(), principal.getImgUrl()));
            user = userService.findThirdUserByTypeAndOpenid(principal.getType(), principal.getOpenid());
        }
        if (user == null) {
            throw new InternalAuthenticationServiceException("第三方帐户创建失败");
        }
        //check 签名
        checkSignature(user.getOpenid(), credentials);


        ThirdUserAuthenticationToken successToken = new ThirdUserAuthenticationToken(user, authentication.getCredentials(),
                AuthorityUtils.createAuthorityList("ROLE_USER"));
        successToken.setDetails(authentication.getDetails());
        return successToken;
    }

    private void checkSignature(String openid, ThirdUserCredentials credentials) {
        StringBuilder data = new StringBuilder();
        data.append(SIGNATURE_SALT);
        data.append(credentials.getSignatureKey());
        data.append(openid);

        String rightSignature = DigestUtils.md5DigestAsHex(data.toString().getBytes());
        if (!StringUtils.equals(rightSignature, credentials.getSignature())) {
            throw new BadCredentialsException("签名校验不通过");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ThirdUserAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
