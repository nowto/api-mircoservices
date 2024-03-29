package com.xunlu.api.user.repository;

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
    public String findOne(User user) {
        return (String) redisTemplate.opsForValue().get(keyTokenByUserId(user.getId()));
    }

    @Override
    public void addToken(User user, String token) {
        redisTemplate.opsForValue().set(
                keyTokenByUserId(user.getId()),
                token);
        redisTemplate.opsForValue().set(keyUserByToken(token), user);
    }

    private String keyUserByToken(String token) {
        return "user:token:" + token;
    }

    private String keyTokenByUserId(Integer userId) {
        return "token:userId:" + String.valueOf(userId);
    }
}
