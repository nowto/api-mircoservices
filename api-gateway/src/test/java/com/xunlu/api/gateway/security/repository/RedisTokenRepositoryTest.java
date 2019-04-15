package com.xunlu.api.gateway.security.repository;

import com.xunlu.api.gateway.security.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RedisTokenRepositoryTest {

    @Test
    public void getUserByToken() {
        User storedUser = new User();
        storedUser.setId(5555);

        ValueOperations<String, Object> opsForValue = mock(ValueOperations.class);
        when(opsForValue.get("user:token:hello")).thenReturn(storedUser);

        RedisTemplate<String, Object> redisTemplate = mock(RedisTemplate.class);
        when(redisTemplate.opsForValue()).thenReturn(opsForValue);

        RedisTokenRepository tokenRepository = new RedisTokenRepository(redisTemplate);
        User user = tokenRepository.getUserByToken("hello");

        Assert.assertNotNull(user);
        Assert.assertTrue(user.getId().equals(5555));

        Assert.assertNull(tokenRepository.getUserByToken("88888"));
    }
}