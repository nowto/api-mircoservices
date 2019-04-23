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

    public UserNotExistException() {
        super("用户不存在");
    }

    public UserNotExistException(Integer userId) {
        super("id为"+ userId + "的用户不存在");
    }
}
