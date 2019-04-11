package com.xunlu.api.user.service;

import com.xunlu.api.user.domain.AccessToken;
import com.xunlu.api.user.domain.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractTokenServiceTest {

    @Test(expected = TokenCreationException.class)
    public void shoudThrowExceptionWhenUserIsNull() {
        TestTokenServiceImpl tokenService = new TestTokenServiceImpl();
        tokenService.generateToken(null);
    }

    @Test(expected = TokenCreationException.class)
    public void shoudThrowExceptionWhenUserIdIsNull() {
        TestTokenServiceImpl tokenService = new TestTokenServiceImpl();
        User user = new User();
        user.setId(null);
        tokenService.generateToken(user);
    }

    @Test
    public void shoudSuccessWhenUserHasId() {
        TestTokenServiceImpl tokenService = new TestTokenServiceImpl();
        User user = new User();
        user.setId(1);
        AccessToken token = tokenService.generateToken(user);
        assertNotNull(token);
        assertNotNull(token.getToken());
    }

    static class TestTokenServiceImpl extends  AbstractTokenService {
        @Override
        public AccessToken getExistingOrGenerateNew(User user) {
            // 实现该方法,为了编译通过
            return null;
        }
    }
}