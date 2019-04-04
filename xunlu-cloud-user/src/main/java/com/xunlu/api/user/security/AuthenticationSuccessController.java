package com.xunlu.api.user.security;

import com.xunlu.api.user.resource.RestfulError;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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

    @RequestMapping(Config.AUTHENTICATION_SUCCESS_FORWARD_URL)
    @ResponseBody
     handleSuccess() {

    }
}
