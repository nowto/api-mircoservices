package com.xunlu.api.gateway.security.repository;

import com.xunlu.api.user.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

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

    @Test
    public void deleteTokenByUserId() {
        ValueOperations<String, Object> opsForValue = mock(ValueOperations.class);
        when(opsForValue.get("token:userId:4")).thenReturn("hello");

        RedisTemplate<String, Object> redisTemplate = mock(RedisTemplate.class);
        when(redisTemplate.opsForValue()).thenReturn(opsForValue);

        RedisTokenRepository tokenRepository = new RedisTokenRepository(redisTemplate);

        tokenRepository.deleteToken(4);

        verify(redisTemplate).delete("user:token:hello");
        verify(redisTemplate).delete("token:userId:4");
    }

    @Test
    public void deleteTokenByToken() {
        User u = new User();
        u.setId(55555);

        ValueOperations<String, Object> opsForValue = mock(ValueOperations.class);
        when(opsForValue.get("user:token:hello")).thenReturn(u);

        RedisTemplate<String, Object> redisTemplate = mock(RedisTemplate.class);
        when(redisTemplate.opsForValue()).thenReturn(opsForValue);

        RedisTokenRepository tokenRepository = new RedisTokenRepository(redisTemplate);

        tokenRepository.deleteToken("hello");

        verify(redisTemplate).delete("user:token:hello");
        verify(redisTemplate).delete("token:userId:55555");
    }

    @Test
    public void deleteTokenByTokenWhenTokenNoCorrespondingUser() {

        ValueOperations<String, Object> opsForValue = mock(ValueOperations.class);

        RedisTemplate<String, Object> redisTemplate = mock(RedisTemplate.class);
        when(redisTemplate.opsForValue()).thenReturn(opsForValue);

        RedisTokenRepository tokenRepository = new RedisTokenRepository(redisTemplate);

        tokenRepository.deleteToken("hello");

        verify(redisTemplate, never()).delete(anyString());
        verify(redisTemplate, never()).delete(anyString());
    }
}