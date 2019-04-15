package com.xunlu.api.gateway.security.repository;

import com.xunlu.api.gateway.security.domain.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * 使用redis实现的TokenRepository.
 *
 * 设计:
 * hash: user:token:[token] -> user
 * string: token:userId:[userId] -> token
 * @author liweibo
 */
@Repository
public class RedisTokenRepository implements TokenRepository {
    private RedisTemplate<String, Object> redisTemplate;


    public RedisTokenRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public User getUserByToken(String token) {
        return (User) redisTemplate.opsForValue().get(keyUserByToken(token));
    }

    private String keyUserByToken(String token) {
        return "user:token:" + token;
    }
}
