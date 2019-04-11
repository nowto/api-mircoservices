package com.xunlu.api.user.repository.mapper;

import com.xunlu.api.user.domain.ThirdUser;
import com.xunlu.api.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Mybatis UserMapper
 * @author liweibo
 */
@Mapper
public interface UserMapper {
    /**
     * 添加用户.
     * 仅操作{@code tb_user}表
     * @param user
     * @return
     */
    boolean addUser(User user);

    /**
     * 添加用户.
     * 仅操作{@code tb_third_user}表
     * @param user
     * @return
     */
    boolean addThirdUser(ThirdUser user);

    /**
     * 获取用户根据id
     * @param id 用户id
     * @return 用户
     */
    User getById(Integer id);

    /**
     * 获取用户根据phone
     * @param phone 用户手机号
     * @return 用户
     */
    User findByPhone(String phone);

    /**
     * 获取第三方登录用户
     * @param type 登录类型
     * @param openid openid
     * @return 第三方登录用户
     */
    ThirdUser findThirdUser(ThirdUser.Type type, String openid);

    /**
     * 获取用户密码,根据id
     * @param id
     * @return 用户密码, 如果指定id用户不存在或密码为null,返回null
     */
    String findPassword(Integer id);

    /**
     * 获取用户偏好根据用户id
     * @param id 用户id
     * @return 用户偏好,如果不存在,返回null
     */
    User.Prefer getUserPrefer(@Param("id") Integer id);

    /**
     * 设置云通信的用户标识.
     * 如果identifier不为null,tim_sync会置为1,
     * identifier允许为null, 如果identifier为null, tim_sync会置为0.
     * @param id
     * @param identifier
     * @return
     */
    boolean updateTIMIdentifier(@Param("id") int id, @Nullable @Param("identifier") String identifier);

    /**
     * 更新用户的偏好.
     * 只会更新prefer中不为null的属性
     * @param id 欲更新用户的用户id
     * @param prefer 用户偏好 不能为null,且其
     *               preferNatural,
     *               preferHuman,
     *               preferRunning,
     *               preferPlayTime,
     *               preferNightPlay,
     *               preferPubTransFirst,
     *               preferHotelLevel,
     *               preferTripNumber,
     *               preferFlight
     *               属性中,至少要有一个不为null
     * @return true 更新成功, false 更新失败
     */
    boolean updatePrefer(@Param("id") Integer id, @NonNull @Param("prefer") User.Prefer prefer);

    /**
     * 更新用户的密码.
     * @param id 欲更新用户的用户id
     * @param password 欲更新后的密码. 注意, 如果传入null,会将密码更新为null
     * @return ture 更新成功, false 更新失败
     */
    boolean updatePassword(@Param("id") Integer id, @Param("password") String password);

    /**
     * 更新用户的昵称.
     * @param id 欲更新用户的用户id
     * @param nickName 欲更新后的昵称. 注意, 如果传入null,会将昵称更新为null
     * @return ture 更新成功, false 更新失败
     */
    boolean updateNickName(@Param("id") Integer id, @Param("nickName") String nickName);

    /**
     * 更新用户的personSign.
     * @param id 欲更新用户的用户id
     * @param personSign 注意, 如果传入null,会将personSign更新为null
     * @return ture 更新成功, false 更新失败
     */
    boolean updatePersonSign(@Param("id") Integer id, @Param("personSign") String personSign);

    /**
     * 更新用户的头像
     * @param id 欲更新用户的用户id
     * @param photo 更新后头像. 注意, 如果传入null,会将personSign更新为null
     * @return ture 更新成功, false 更新失败
     */
    boolean updatePhoto(@Param("id") Integer id, @Param("photo") String photo);
}
