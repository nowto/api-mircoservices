package com.xunlu.api.user.mapper;

import com.xunlu.api.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * Mybatis UserMapper
 * @author liweibo
 */
@Mapper
public interface UserMapper {
    /**
     * 添加用户
     * @param user
     * @return
     */
    boolean addUser(User user);

    /**
     * 查找一个用户.
     * 将使用example的每个不为null的属性做等值条件查询, 如果查找到多个,将只返回第一个
     * @param example 条件
     * @return
     */
    User findOne(User example);

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
}
