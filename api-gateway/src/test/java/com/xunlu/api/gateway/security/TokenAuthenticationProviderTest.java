package com.xunlu.api.gateway.security;

import com.xunlu.api.gateway.security.domain.User;
import com.xunlu.api.gateway.security.service.TokenService;
import org.junit.Test;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import static org.junit.Assert.*;

public class TokenAuthenticationProviderTest {
    @Test
    public void testAuthenticateFailsForIncorrectToken() {
        TokenAuthenticationToken token = new TokenAuthenticationToken("INVALID_TOKEN");
        TokenAuthenticationProvider provider = new TokenAuthenticationProvider(new MockTokenService());
        try {
            provider.authenticate(token);
            fail("Should have thrown InternalAuthenticationServiceException");
        }
        catch (InternalAuthenticationServiceException expected) {
            assertEquals("指定token获取不到用户", expected.getMessage());
        }
    }

    @Test
    public void testReceivedBadCredentialsWhenTokenIsNull() {
        TokenAuthenticationToken token = new TokenAuthenticationToken(null);
        TokenAuthenticationProvider provider = new TokenAuthenticationProvider(new MockTokenService());

        try {
            provider.authenticate(token);
            fail("Expected BadCredenialsException");
        }
        catch (InternalAuthenticationServiceException expected) {
            assertEquals("获取不到token", expected.getMessage());
        }
    }


    @Test
    public void testAuthenticates() {
        TokenAuthenticationToken token = new TokenAuthenticationToken("token");
        token.setDetails("192.168.0.1");

        TokenAuthenticationProvider provider = new TokenAuthenticationProvider(new MockTokenService());
        provider.authenticate(token);

        Authentication result = provider.authenticate(token);

        if (!(result instanceof TokenAuthenticationToken)) {
            fail("Should have returned instance of TokenAuthenticationToken");
        }

        TokenAuthenticationToken castResult = (TokenAuthenticationToken) result;


        assertEquals(User.class, castResult.getPrincipal().getClass());
        assertEquals("token", castResult.getCredentials());
        assertTrue(AuthorityUtils.authorityListToSet(castResult.getAuthorities()).contains("ROLE_USER"));
        assertEquals("192.168.0.1", castResult.getDetails());
    }

    @Test
    public void testAuthenticatesASecondTime() {
        TokenAuthenticationToken token = new TokenAuthenticationToken("token");
        token.setDetails("192.168.0.1");

        TokenAuthenticationProvider provider = new TokenAuthenticationProvider(new MockTokenService());
        provider.authenticate(token);

        Authentication result = provider.authenticate(token);

        if (!(result instanceof TokenAuthenticationToken)) {
            fail("Should have returned instance of TokenAuthenticationToken");
        }

        // Now try to authenticate with the previous result
        Authentication result2 = provider.authenticate(result);

        if (!(result2 instanceof TokenAuthenticationToken)) {
            fail("Should have returned instance of TokenAuthenticationToken");
        }
        assertEquals(result.getCredentials(), result2.getCredentials());
    }


    private class MockTokenService implements TokenService {
        @Override
        public User getUser(String token) {
            if ("token".equals(token)) {
                User u = new User();
                u.setId(777);
                return u;
            }
            return null;
        }
    }
}