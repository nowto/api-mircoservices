package com.xunlu.api.user.service;

import com.xunlu.api.user.domain.AccessToken;
import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.repository.TokenRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TokenServiceImplTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Test
    public void testExisting() {
        User user = new User();
        user.setId(1);
        AccessToken existingToken = new AccessToken("hello");
        when(tokenRepository.findOne(user)).thenReturn(existingToken);


        AccessToken token = tokenService.getExistingOrGenerateNew(user);
        Assert.assertEquals(existingToken, token);
        verify(tokenRepository, never()).addToken(any(), any());
    }

    @Test
    public void testGenerateNew() {
        User user = new User();
        user.setId(1);
        when(tokenRepository.findOne(user)).thenReturn(null);


        AccessToken token = tokenService.getExistingOrGenerateNew(user);
        Assert.assertNotNull(token);
        Assert.assertNotNull(token.getToken());
        verify(tokenRepository).addToken(eq(user), eq(token.getToken()));
    }

    @Test(expected = UserNotExistException.class)
    public void testExceptionWhenUserIsNull() {
        tokenService.getExistingOrGenerateNew(null);
    }

    @Test(expected = TokenCreationException.class)
    public void testExceptionWhenUserIdIsNull() {
        User user = new User();
        when(tokenRepository.findOne(user)).thenReturn(null);


        tokenService.getExistingOrGenerateNew(user);
    }
}