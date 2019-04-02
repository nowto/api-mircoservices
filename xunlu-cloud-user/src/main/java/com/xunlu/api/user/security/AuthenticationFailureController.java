package com.xunlu.api.user.security;

import com.xunlu.api.user.resource.RestfulError;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 处理认证失败
 * @author liweibo
 */
@Controller
public class AuthenticationFailureController {

    @RequestMapping(Config.AUTHENTICATION_FAILURE_FORWARD_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    RestfulError handleFailure(
            @RequestAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) AuthenticationException exception) {
        return new RestfulError(exception.getMessage());
    }
}
