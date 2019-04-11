package com.xunlu.api.user.service;

import com.xunlu.api.user.domain.User;

public class UserNotExistException extends ServiceException{
    private User user;
    private Integer userId;

    public UserNotExistException() {
    }

    public UserNotExistException(String message) {
        super(message);
    }

    public UserNotExistException(Integer userId, String message) {
        super(message);
        this.userId = userId;
    }
    public UserNotExistException(User user, String message) {
        super(message);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Integer getUserId() {
        if (userId == null) {
            userId = user != null ? user.getId() : null;
        }
        return userId;
    }
}
