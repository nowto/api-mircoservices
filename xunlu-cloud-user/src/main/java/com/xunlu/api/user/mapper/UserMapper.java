package com.xunlu.api.user.mapper;

import com.xunlu.api.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liweibo
 */
@Mapper
public interface UserMapper {
    Integer addUser(User user);

    List<User> findList(User user);

    User findOne(User user);

    User findUserPrefer(Integer userId);

    int updateUser(User user);

    String findPassword(Integer userId);

    void updatePassword(@Param("userId")Integer userId, @Param("password")String password);

    void updateNickName(@Param("userId")Integer userId, @Param("nickName")String nickName);

    void updatePersonSign(@Param("userId")Integer userId, @Param("personSign")String personSign);

    void updatePhoto(@Param("userId")Integer userId, @Param("photo")String photo);
}
