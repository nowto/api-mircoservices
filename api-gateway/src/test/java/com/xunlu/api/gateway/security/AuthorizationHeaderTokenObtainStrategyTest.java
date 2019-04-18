package com.xunlu.api.gateway.security;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;

public class AuthorizationHeaderTokenObtainStrategyTest {

    @Test
    public void obtianToken() {
        MockHttpServletRequest requestWithHeader = new MockHttpServletRequest("GET", "/hello");
        requestWithHeader.addHeader(HttpHeaders.AUTHORIZATION, "token");

        TokenObtainStrategy strategy = new AuthorizationHeaderTokenObtainStrategy();
        String token = strategy.obtianToken(requestWithHeader);
        Assert.assertEquals("token", token);

        token = strategy.obtianToken(new MockHttpServletRequest());
        Assert.assertNull(token);
    }
}