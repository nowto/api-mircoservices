package com.xunlu.api.user.security;

import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.service.ServiceException;
import com.xunlu.api.user.service.SmsService;
import com.xunlu.api.user.service.UserService;
import org.junit.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import static org.junit.Assert.*;

public class SmsCodeAuthenticationProviderTest {

    @Test
    public void testAuthenticateFailsForIncorrectPassword() {
        SmsCodeAuthenticationToken token = new SmsCodeAuthenticationToken("17777777777", new SmsCredentials("1111", "86", "2222"));
        SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider(new MockUserService(), new MockSmsService());
        try {
            provider.authenticate(token);
            fail("Should have thrown BadCredentialsException");
        }
        catch (BadCredentialsException expected) {

        }
    }

    @Test
    public void testReceivedBadCredentialsWhenCredentialsNotProvided() {
        SmsCodeAuthenticationToken token = new SmsCodeAuthenticationToken("17777777777", null);
        SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider(new MockUserService(), new MockSmsService());

        try {
            provider.authenticate(token);
            fail("Expected BadCredenialsException");
        }
        catch (BadCredentialsException expected) {

        }
    }

    @Test
    public void testAuthenticateFailsWithEmptyPhone() {
        SmsCodeAuthenticationToken token = new SmsCodeAuthenticationToken(null, new SmsCredentials("1111", "86", "2222"));
        SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider(new MockUserService(), new MockSmsService());

        try {
            provider.authenticate(token);
            fail("Should have thrown InternalAuthenticationServiceException");
        }
        catch (InternalAuthenticationServiceException expected) {

        }
    }

    @Test
    public void testAuthenticateFailsWithInvalidPhone() {
        SmsCodeAuthenticationToken token = new SmsCodeAuthenticationToken("INVALID_PHONE", new SmsCredentials("1111", "86", "1111"));
        SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider(new MockUserService(), new MockSmsService());

        try {
            provider.authenticate(token);
            fail("Should have thrown InternalAuthenticationServiceException");
        }
        catch (InternalAuthenticationServiceException expected) {

        }
    }

    @Test
    public void testAuthenticates() {
        SmsCodeAuthenticationToken token = new SmsCodeAuthenticationToken("17777777777", new SmsCredentials("1111", "86", "1111"));
        token.setDetails("192.168.0.1");

        SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider(new MockUserService(), new MockSmsService());
        provider.authenticate(token);

        Authentication result = provider.authenticate(token);

        if (!(result instanceof SmsCodeAuthenticationToken)) {
            fail("Should have returned instance of SmsCodeAuthenticationToken");
        }

        SmsCodeAuthenticationToken castResult = (SmsCodeAuthenticationToken) result;


        assertEquals(User.class, castResult.getPrincipal().getClass());
        assertEquals("1111", ((SmsCredentials)castResult.getCredentials()).getCode());
        assertTrue(AuthorityUtils.authorityListToSet(castResult.getAuthorities()).contains("ROLE_USER"));
        assertEquals("192.168.0.1", castResult.getDetails());
    }

    @Test
    public void testAuthenticatesASecondTime() {
        SmsCodeAuthenticationToken token = new SmsCodeAuthenticationToken("17777777777", new SmsCredentials("1111", "86", "1111"));
        token.setDetails("192.168.0.1");

        SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider(new MockUserService(), new MockSmsService());
        provider.authenticate(token);

        Authentication result = provider.authenticate(token);

        if (!(result instanceof SmsCodeAuthenticationToken)) {
            fail("Should have returned instance of SmsCodeAuthenticationToken");
        }

        // Now try to authenticate with the previous result (with its UserDetails)
        Authentication result2 = provider.authenticate(result);

        if (!(result2 instanceof SmsCodeAuthenticationToken)) {
            fail("Should have returned instance of SmsCodeAuthenticationToken");
        }
        assertEquals(result.getCredentials(), result2.getCredentials());
    }



    private class MockUserService implements UserService {

        @Override
        public void addUser(User user) {

        }

        @Override
        public User getUser(Integer id) {
            return null;
        }

        @Override
        public User findByPhone(String phone) throws IllegalArgumentException {
            if ("17777777777".equals(phone)) {
                User user = new User();
                user.setPhone("17777777777");
                user.setId(1);
                return user;
            }
            return null;
        }

        @Override
        public String findPassword(Integer id) {
            return null;
        }

        @Override
        public Integer checkLiked(Integer userId, Integer uid) {
            return null;
        }

        @Override
        public User.Prefer getUserPrefer(Integer id) {
            return null;
        }

        @Override
        public boolean updatePrefer(Integer id, User.Prefer prefer) {
            return false;
        }

        @Override
        public boolean updatePassword(Integer id, String password) {
            return false;
        }

        @Override
        public boolean updateNickName(Integer id, String nickName) {
            return false;
        }

        @Override
        public boolean updatePersonSign(Integer id, String personSign) {
            return false;
        }

        @Override
        public boolean updatePhoto(Integer id, String photo) {
            return false;
        }
    }

    private class MockSmsService implements SmsService {
        @Override
        public boolean verify(String appKey, String zone, String phone, String code) throws ServiceException {
            if ("1111".equals(code)) {
                return true;
            }
            return false;
        }
    }

}