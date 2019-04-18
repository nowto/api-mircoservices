package com.xunlu.api.user.repository;

import com.xunlu.api.user.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.mockito.Mockito.*;

public class RedisTokenRepositoryTest {

    @Test
    public void findOne() {
        ValueOperations<String, Object> opsForValue = mock(ValueOperations.class);
        when(opsForValue.get("token:userId:4")).thenReturn("hello");

        RedisTemplate<String, Object> redisTemplate = mock(RedisTemplate.class);
        when(redisTemplate.opsForValue()).thenReturn(opsForValue);

        RedisTokenRepository tokenRepository = new RedisTokenRepository(redisTemplate);
        User user = new User();
        user.setId(4);

        String token = tokenRepository.findOne(user);

        Assert.assertEquals("hello", token);
    }

    @Test
    public void addToken() {
        ValueOperations<String, Object> opsForValue = mock(ValueOperations.class);

        RedisTemplate<String, Object> redisTemplate = mock(RedisTemplate.class);
        when(redisTemplate.opsForValue()).thenReturn(opsForValue);

        RedisTokenRepository tokenRepository = new RedisTokenRepository(redisTemplate);

        User u = new User();
        u.setId(4);
        tokenRepository.addToken(u, "hello");

        verify(opsForValue).set("token:userId:4", "hello");
        verify(opsForValue).set("user:token:hello", u);
    }

}