package com.xunlu.api.gateway.security.repository;

import com.xunlu.api.user.domain.User;
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

    @Override
    public void deleteToken(Integer userId) {
        String token = (String) redisTemplate.opsForValue().get(keyTokenByUserId(userId));
        deleteToken(userId, token);
    }

    @Override
    public void deleteToken(String token) {
        User user = (User) redisTemplate.opsForValue().get(keyUserByToken(token));
        if (user == null) {
            return;
        }
        Integer userId = user.getId();
        deleteToken(userId, token);
    }

    private void deleteToken(Integer userId, String token) {
        redisTemplate.delete(keyUserByToken(token));
        redisTemplate.delete(keyTokenByUserId(userId));
    }

    private String keyUserByToken(String token) {
        return "user:token:" + token;
    }

    private String keyTokenByUserId(Integer userId) {
        return "token:userId:" + String.valueOf(userId);
    }
}
