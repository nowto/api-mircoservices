package com.xunlu.api.user.service;

import com.xunlu.api.user.domain.AccessToken;
import com.xunlu.api.user.domain.User;
import org.springframework.lang.NonNull;

/**
 * {@link com.xunlu.api.user.domain.AccessToken}服务类
 * @author liweibo
 */
public interface TokenService {

    /**
     * 获取{@code user}已经存在的token,如果不存在,就生成一个新的.
     *
     * @param user 将创建针对该用户的token
     * @return 该用于的token,可能为已存在的,也可能是新创建的. 永远不会返回{@code null}
     * @throws UserNotExistException 传入{@code user}为null抛出此异常
     * @throws TokenCreationException 生成{@code user}token出错
     */
    AccessToken getExistingOrGenerateNew(@NonNull User user) throws ServiceException;
}
