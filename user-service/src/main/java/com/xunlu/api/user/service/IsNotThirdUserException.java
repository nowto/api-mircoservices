package com.xunlu.api.user.service;

import com.xunlu.api.common.restful.exception.ServiceException;
import org.springframework.http.HttpStatus;

/**
 * @author liweibo
 */
public class IsNotThirdUserException extends ServiceException {
    {
        status = HttpStatus.CONFLICT;
    }
    public IsNotThirdUserException() {
        super("该用户不是第三方登录用户");
    }
}
