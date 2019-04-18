package com.xunlu.api.user.service;

import org.springframework.http.HttpStatus;

/**
 * 查找不到用户异常
 * @author liweibo
 */
public class UserNotExistException extends ServiceException{
    {
        status = HttpStatus.NOT_FOUND;
    }

    public UserNotExistException(String message) {
        super(message);
    }

}
