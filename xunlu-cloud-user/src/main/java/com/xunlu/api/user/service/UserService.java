package com.xunlu.api.user.service;

import com.xunlu.api.user.domain.User;

/**
 * 用户{@link com.xunlu.api.user.domain.User}类 服务接口
 * @author liweibo
 */
public interface UserService {
    /**
     * 添加一个新用户.
     * 同时也会将这个帐号导入到腾讯云通信IM, 如果可以成功导入的话
     * @param user 新用户
     */
    void addUser(User user);

    /**
     * 根据用户id获取用户
     * @param id 用户id
     * @return 用户,如果指定id用户不存在,返回null
     */
    User getUser(Integer id);

    User findByPhone(String phone);

    User findUserPrefer(Integer userId);

    int updateUser(User user);

    String findPassword(Integer userId);

    void updatePassword(Integer userId, String password);


    void updateNickName(Integer userId, String nickName);

    void updatePersonSign(Integer userId, String personSign);

    void updatePhoto(Integer userId, String photo);
    User findById(Integer userId);
    Integer checkLiked(Integer userId, Integer uid);
}
