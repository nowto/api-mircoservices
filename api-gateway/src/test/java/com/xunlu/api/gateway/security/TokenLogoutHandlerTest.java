package com.xunlu.api.gateway.security;

import com.xunlu.api.gateway.security.service.TokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TokenLogoutHandlerTest {

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private TokenLogoutHandler tokenLogoutHandler;

    @Test
    public void logoutNormal() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "hello");

        tokenLogoutHandler.logout(request, null, null);

        verify(tokenService).invalidateToken("hello");
    }

    @Test(expected = IllegalArgumentException.class)
    public void logoutWhenRequestNull() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "hello");

        tokenLogoutHandler.logout(null, null, null);
    }

    @Test
    public void logoutWhenRequestLackToken() {

        MockHttpServletRequest request = new MockHttpServletRequest();

        tokenLogoutHandler.logout(request, null, null);

        verify(tokenService, never()).invalidateToken(anyString());
    }
}