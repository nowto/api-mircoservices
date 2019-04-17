package com.xunlu.api.user.service;

import com.xunlu.api.user.domain.ThirdUser;
import com.xunlu.api.user.domain.User;
import org.springframework.lang.NonNull;

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

    /**
     * 根据手机号获取用户
     * @param phone 手机号,不可以为null
     * @return 用户,如果指定phone用户不存的,返回null
     * @throws IllegalArgumentException 如果传参为null
     */
    User findByPhone(@NonNull String phone) throws IllegalArgumentException;

    /**
     * 根据登录类型和openid获取第三方登录用户
     * @param type 登录类型， 不可以为null
     * @param openid 第三方openid， 不可以为null
     * @return 第三方用户， 如果该条件用户不存在， 返回null
     * @throws IllegalArgumentException 如果传参为null
     */
    ThirdUser findThirdUserByTypeAndOpenid(ThirdUser.Type type, String openid);

    /**
     * 根据用户id获取用户密码
     * @param id 用户id
     * @return 用户密码, 如果指定id用户不存在或密码为null,返回null
     */
    String findPassword(Integer id);

    Integer checkLiked(Integer userId, Integer uid);

    /**
     * 根据用户id获取用户偏好
     * @param id 用户id
     * @return 用户的偏好, 如果指定id用户不存在,返回null
     */
    User.Prefer getUserPrefer(Integer id);

    /**
     * 更新用户的偏好.
     * 只会更新prefer中不为null的属性
     * @param id 欲更新用户的用户id
     * @param prefer 用户偏好
     * @return true 更新成功, false 更新失败
     */
    boolean updatePrefer(Integer id, User.Prefer prefer);

    /**
     * 更新用户的密码.
     * @param id 欲更新用户的用户id
     * @param password 更新后的密码. 注意, 如果传入null, 会将密码更新为null
     * @return ture 更新成功, false 更新失败
     */
    boolean updatePassword(Integer id, String password);

    /**
     * 更新用户的昵称
     * @param id 欲更新用户的用户id
     * @param nickName 更新后的昵称, 注意, 如果传入null, 会将昵称更新为null
     * @return true 更新成功, false 更新失败
     */
    boolean updateNickName(Integer id, String nickName);

    /**
     * 更新用户的personSign
     * @param id 欲更新用户的用户id
     * @param personSign 注意, 如果传入null, 会将personSign更新为null
     */
    boolean updatePersonSign(Integer id, String personSign);

    /**
     * 更新用户的头像
     * @param id 欲更新用户的用户id
     * @param photo 更新后的头像, 注意, 如果传入null, 会将头像更新为null
     * @return true 更新成功, false 更新失败
     */
    boolean updatePhoto(Integer id, String photo);

    /**
     * 更新第三方登录用户的openid
     * @param id 欲更新第三方登录用户的用户id, 不允许为null
     * @param openid 更新后openid
     * @return true 成功, false 失败
     * @throws ServiceException 该id用户不是第三方登录用户
     */
    boolean updateThirdUserOpenid(Integer id, @NonNull String openid) throws ServiceException;
}
