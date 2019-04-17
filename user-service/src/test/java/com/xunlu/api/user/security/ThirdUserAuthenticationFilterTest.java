package com.xunlu.api.user.security;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ThirdUserAuthenticationFilterTest {
    @Test
    public void testNormalOperation() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/token");

        ThirdUserAuthenticationFilter filter = new ThirdUserAuthenticationFilter();
        filter.setAuthenticationManager(createAuthenticationManager());
        // filter.init(null);

        Authentication result = filter.attemptAuthentication(request,
                new MockHttpServletResponse());
        assertNotNull(result);
        assertEquals(((WebAuthenticationDetails) result.getDetails()).getRemoteAddress(), "127.0.0.1");
    }

    @Test
    public void testNullCredentialsHandledGracefully() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/token");

        ThirdUserAuthenticationFilter filter = new ThirdUserAuthenticationFilter();
        filter.setAuthenticationManager(createAuthenticationManager());
        assertNotNull(filter
                .attemptAuthentication(request, new MockHttpServletResponse()));
    }

    @Test
    public void testNullPrincipalHandledGracefully() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/token");

        ThirdUserAuthenticationFilter filter = new ThirdUserAuthenticationFilter();
        filter.setAuthenticationManager(createAuthenticationManager());
        assertNotNull(filter
                .attemptAuthentication(request, new MockHttpServletResponse()));
    }

    @Test
    public void testSpacesAreTrimmedCorrectlyFromOpenid() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/token");
        request.addParameter(ThirdUserAuthenticationFilter.SECURITY_OPENID, " hello   ");

        ThirdUserAuthenticationFilter filter = new ThirdUserAuthenticationFilter();
        filter.setAuthenticationManager(createAuthenticationManager());

        Authentication result = filter.attemptAuthentication(request,
                new MockHttpServletResponse());
    }

    @Test
    public void testFailedAuthenticationThrowsException() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/token");
        ThirdUserAuthenticationFilter filter = new ThirdUserAuthenticationFilter();
        AuthenticationManager am = mock(AuthenticationManager.class);
        when(am.authenticate(any(Authentication.class))).thenThrow(
                new BadCredentialsException(""));
        filter.setAuthenticationManager(am);

        try {
            filter.attemptAuthentication(request, new MockHttpServletResponse());
            fail("Expected AuthenticationException");
        } catch (AuthenticationException e) {
        }
    }

    @Test
    public void noSessionIsCreatedIfAllowSessionCreationIsFalse() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/token");

        ThirdUserAuthenticationFilter filter = new ThirdUserAuthenticationFilter();
        filter.setAllowSessionCreation(false);
        filter.setAuthenticationManager(createAuthenticationManager());

        filter.attemptAuthentication(request, new MockHttpServletResponse());

        assertNull(request.getSession(false));
    }


    private AuthenticationManager createAuthenticationManager() {
        AuthenticationManager am = mock(AuthenticationManager.class);
        when(am.authenticate(any(Authentication.class))).thenAnswer(
                new Answer<Authentication>() {
                    public Authentication answer(InvocationOnMock invocation)
                            throws Throwable {
                        return (Authentication) invocation.getArguments()[0];
                    }
                });

        return am;
    }

}