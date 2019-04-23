package com.xunlu.api.user.security;

import com.xunlu.api.user.domain.ThirdUser;
import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.repository.mapper.UserMapper;
import com.xunlu.api.user.service.ServiceException;
import com.xunlu.api.user.service.TencentIMService;
import com.xunlu.api.user.service.UserService;
import com.xunlu.api.user.service.UserServiceImpl;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ThirdUserAuthenticationProviderTest {
    private static final String rightSign = "62a5d3aa156ec1d9d95110ccdc1ca6ff";
    @Test
    public void testAuthenticateFailsForIncorrectCredentials() {
        ThirdUserPrincipal principal = new ThirdUserPrincipal(ThirdUser.Type.WEIBO, "weibo", "weibo", "weibo");
        ThirdUserCredentials credentials = new ThirdUserCredentials("INCORRECT", ThirdUserCredentials.DEFAULT_SIGNATURE_KEY);
        ThirdUserAuthenticationToken token = new ThirdUserAuthenticationToken(principal, credentials);
        ThirdUserAuthenticationProvider provider = new ThirdUserAuthenticationProvider(new ThirdUserAuthenticationProviderTest.MockUserService());
        try {
            provider.authenticate(token);
            fail("Should have thrown BadCredentialsException");
        }
        catch (BadCredentialsException expected) {

        }
    }

    @Test
    public void testReceivedBadCredentialsWhenCredentialsNotProvided() {
        ThirdUserPrincipal principal = new ThirdUserPrincipal(ThirdUser.Type.WEIBO, "weibo", "weibo", "weibo");
        ThirdUserAuthenticationToken token = new ThirdUserAuthenticationToken(principal, null);
        ThirdUserAuthenticationProvider provider = new ThirdUserAuthenticationProvider(new ThirdUserAuthenticationProviderTest.MockUserService());

        try {
            provider.authenticate(token);
            fail("Expected BadCredenialsException");
        }
        catch (BadCredentialsException expected) {

        }
    }

    @Test
    public void testAuthenticateFailsWithEmptyOpenid() {
        ThirdUserPrincipal principal = new ThirdUserPrincipal(ThirdUser.Type.WEIBO, null, "weibo", "weibo");
        ThirdUserCredentials credentials = new ThirdUserCredentials(rightSign, ThirdUserCredentials.DEFAULT_SIGNATURE_KEY);
        ThirdUserAuthenticationToken token = new ThirdUserAuthenticationToken(principal, credentials);
        ThirdUserAuthenticationProvider provider = new ThirdUserAuthenticationProvider(new ThirdUserAuthenticationProviderTest.MockUserService());

        try {
            provider.authenticate(token);
            fail("Should have thrown AuthenticationException");
        }
        catch (AuthenticationException expected) {

        }
    }

    @Test
    public void testAuthenticateFailsWithEmptyType() {
        ThirdUserPrincipal principal = new ThirdUserPrincipal(null, "weibo", "weibo", "weibo");
        ThirdUserCredentials credentials = new ThirdUserCredentials(rightSign, ThirdUserCredentials.DEFAULT_SIGNATURE_KEY);
        ThirdUserAuthenticationToken token = new ThirdUserAuthenticationToken(principal, credentials);
        ThirdUserAuthenticationProvider provider = new ThirdUserAuthenticationProvider(new ThirdUserAuthenticationProviderTest.MockUserService());

        try {
            provider.authenticate(token);
            fail("Should have thrown AuthenticationException");
        }
        catch (AuthenticationException expected) {

        }
    }


    @Test
    public void testAuthenticates() {

        ThirdUserPrincipal principal = new ThirdUserPrincipal(ThirdUser.Type.WEIBO, "weibo", "weibo", "weibo");
        ThirdUserCredentials credentials = new ThirdUserCredentials(rightSign, ThirdUserCredentials.DEFAULT_SIGNATURE_KEY);
        ThirdUserAuthenticationToken token = new ThirdUserAuthenticationToken(principal, credentials);
        token.setDetails("192.168.0.1");

        ThirdUserAuthenticationProvider provider = new ThirdUserAuthenticationProvider(new ThirdUserAuthenticationProviderTest.MockUserService());

        Authentication result = provider.authenticate(token);

        if (!(result instanceof ThirdUserAuthenticationToken)) {
            fail("Should have returned instance of ThirdUserAuthenticationToken");
        }

        ThirdUserAuthenticationToken castResult = (ThirdUserAuthenticationToken) result;


        assertTrue(User.class.isAssignableFrom(castResult.getPrincipal().getClass()));
        assertEquals(ThirdUser.class, castResult.getPrincipal().getClass());
        assertEquals(rightSign, ((ThirdUserCredentials)castResult.getCredentials()).getSignature());
        assertTrue(AuthorityUtils.authorityListToSet(castResult.getAuthorities()).contains("ROLE_USER"));
        assertEquals("192.168.0.1", castResult.getDetails());
    }

    @Test
    public void testPlatamId() {

        ThirdUserPrincipal principal = new ThirdUserPrincipal(ThirdUser.Type.WEIBO, "weibo", "unionid", "weibo", "weibo");
        ThirdUserCredentials credentials = new ThirdUserCredentials(rightSign, ThirdUserCredentials.DEFAULT_SIGNATURE_KEY);
        ThirdUserAuthenticationToken token = new ThirdUserAuthenticationToken(principal, credentials);
        token.setDetails("192.168.0.1");

        ThirdUserAuthenticationProvider provider = new ThirdUserAuthenticationProvider(new ThirdUserAuthenticationProviderTest.MockUserService());

        Authentication result = provider.authenticate(token);

        if (!(result instanceof ThirdUserAuthenticationToken)) {
            fail("Should have returned instance of ThirdUserAuthenticationToken");
        }

        ThirdUserAuthenticationToken castResult = (ThirdUserAuthenticationToken) result;


        assertEquals(ThirdUser.class, castResult.getPrincipal().getClass());

    }


    @Test
    public void testAuthenticatesASecondTime() {
        ThirdUserPrincipal principal = new ThirdUserPrincipal(ThirdUser.Type.WEIBO, "weibo", "weibo", "weibo");
        ThirdUserCredentials credentials = new ThirdUserCredentials(rightSign, ThirdUserCredentials.DEFAULT_SIGNATURE_KEY);
        ThirdUserAuthenticationToken token = new ThirdUserAuthenticationToken(principal, credentials);
        token.setDetails("192.168.0.1");

        ThirdUserAuthenticationProvider provider = new ThirdUserAuthenticationProvider(new ThirdUserAuthenticationProviderTest.MockUserService());

        Authentication result = provider.authenticate(token);

        if (!(result instanceof ThirdUserAuthenticationToken)) {
            fail("Should have returned instance of ThirdUserAuthenticationToken");
        }

        // Now try to authenticate with the previous result
        Authentication result2 = provider.authenticate(result);

        if (!(result2 instanceof ThirdUserAuthenticationToken)) {
            fail("Should have returned instance of ThirdUserAuthenticationToken");
        }
        assertEquals(result.getCredentials(), result2.getCredentials());
    }

    @Test
    public void testNewRegisterUserWhenUserNotExist() {
        ThirdUserPrincipal principal = new ThirdUserPrincipal(ThirdUser.Type.WEIBO, "cannotfound", "weibo", "weibo");
        ThirdUserCredentials credentials = new ThirdUserCredentials("9b2a33ea3199ba801ff8ec527dc8c99c", ThirdUserCredentials.DEFAULT_SIGNATURE_KEY);
        ThirdUserAuthenticationToken token = new ThirdUserAuthenticationToken(principal, credentials);
        token.setDetails("192.168.0.1");

        MockUserService mockUserService = new MockUserService();
        ThirdUserAuthenticationProvider provider = new ThirdUserAuthenticationProvider(mockUserService);

        Authentication t = provider.authenticate(token);

        assertTrue(mockUserService.addUserIncoked);
        assertTrue(t.getPrincipal() instanceof User);
    }


    private class MockTestPlatamIdUserService extends UserServiceImpl {
        private boolean addUserIncoked = false;
        public MockTestPlatamIdUserService() {
            super(null, null);
        }

        public MockTestPlatamIdUserService(UserMapper userMapper, TencentIMService tencentIMService) {
            super(userMapper, tencentIMService);
        }

        @Override
        public ThirdUser findThirdUserByTypeAndOpenid(ThirdUser.Type type, String openid) {
            if (addUserIncoked) {
                ThirdUser user = new ThirdUser();
                user.setType(ThirdUser.Type.WEIBO);
                user.setOpenid("unionid");
                user.setId(1);
                return user;
            }
            return null;
        }

        @Override
        public void addUser(User user) {
            if (user instanceof ThirdUser) {
                addUserIncoked = true;
                assertEquals(((ThirdUser) user).getOpenid(), "unionid");
            }
        }
    }

    private class MockUserService extends UserServiceImpl {

        public MockUserService() {
            super(null, null);
        }

        private boolean addUserIncoked = false;

        @Override
        public void addUser(User user) {
            if (user instanceof ThirdUser) {
                addUserIncoked = true;
            }
        }

        @Override
        public User getUser(Integer id) {
            ThirdUser thirdUser = new ThirdUser();
            thirdUser.setId(1);
            return thirdUser;
        }

        @Override
        public User findByPhone(String phone) throws IllegalArgumentException {
            return null;
        }

        @Override
        public ThirdUser findThirdUserByTypeAndOpenid(ThirdUser.Type type, String openid) {
            if (ThirdUser.Type.WEIBO.equals(type) && "weibo".equals(openid)) {
                ThirdUser user = new ThirdUser();
                user.setType(ThirdUser.Type.WEIBO);
                user.setOpenid("weibo");
                user.setId(1);
                return user;
            }
            if (addUserIncoked) {
                ThirdUser user = new ThirdUser();
                user.setType(ThirdUser.Type.WEIBO);
                user.setOpenid("weibo");
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

        @Override
        public boolean updateThirdUserOpenid(Integer id, String openid) throws ServiceException {
            return true;
        }
    }

}