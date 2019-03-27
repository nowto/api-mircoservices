package com.xunlu.api.user.service;

import com.xunlu.api.user.domain.User;

/**
 * 用户{@link com.xunlu.api.user.domain.User}类 服务接口
 * @author liweibo
 */
public interface UserService {
    void addUser(User user) throws Exception;
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
    /**
     * 用jdbc直接插入user，返回新生成的user ID
     * @param user
     * @return
     * @throws Exception
     */
    public Integer insertUser(final User user) throws Exception;
    Integer checkLiked(Integer userId, Integer uid);
}
