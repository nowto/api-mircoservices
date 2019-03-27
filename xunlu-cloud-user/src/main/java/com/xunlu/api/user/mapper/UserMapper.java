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
     * 设置云通信的用户标识.
     * 如果identifier不为null,tim_sync会置为1,
     * identifier允许为null, 如果identifier为null, tim_sync会置为0.
     * @param id
     * @param identifier
     * @return
     */
    boolean updateTIMIdentifier(@Param("id") int id, @Nullable @Param("identifier") String identifier);
}
