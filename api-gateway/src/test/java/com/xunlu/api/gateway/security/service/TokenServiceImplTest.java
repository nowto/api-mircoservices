package com.xunlu.api.gateway.security.service;

import com.xunlu.api.user.domain.User;
import com.xunlu.api.gateway.security.repository.TokenRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TokenServiceImplTest {
    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Test
    public void getUser() {
        User user = new User();
        user.setId(4444);
        when(tokenRepository.getUserByToken("hello")).thenReturn(user);


        User u = tokenService.getUser("hello");
        Assert.assertNotNull(u);
        Assert.assertEquals(Integer.valueOf(4444), u.getId());

        Assert.assertNull(tokenRepository.getUserByToken("bbbbb"));
    }

    @Test
    public void testInvalidateTokenForUser() {
        tokenService.invalidateTokenForUser(56);

        verify(tokenRepository).deleteToken(56);
    }

    @Test
    public void testInvalidateTokenForToken() {
        tokenService.invalidateToken("hello");

        verify(tokenRepository).deleteToken("hello");
    }
}