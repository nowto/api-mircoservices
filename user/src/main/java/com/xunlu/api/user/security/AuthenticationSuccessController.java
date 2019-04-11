package com.xunlu.api.user.security;

import com.xunlu.api.user.domain.AccessToken;
import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理认证成功.
 * 在{@link Config#configure(HttpSecurity)}中有如下配置
 * <code>
 *     smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(
 *          new ForwardAuthenticationSuccessHandler(AUTHENTICATION_SUCCESS_FORWARD_URL));
 * </code>
 * 会将认证成功的处理转发到该 Controller
 *
 * @see Config#configure(HttpSecurity)
 * @see org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler
 * @author liweibo
 */
@Controller
public class AuthenticationSuccessController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping(Config.AUTHENTICATION_SUCCESS_FORWARD_URL)
    @ResponseBody
    public AccessToken handleSuccess(HttpServletRequest request, HttpServletResponse response) {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        if (auth == null) {
            throw new InternalAuthenticationServiceException("用户没有登录");
        }
        if (!auth.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("用户没有登录");
        }
        User user = (User) auth.getPrincipal();
        if (user == null) {
            throw new InternalAuthenticationServiceException("用户没有登录");
        }
        return tokenService.getExistingOrGenerateNew(user);
    }
}
