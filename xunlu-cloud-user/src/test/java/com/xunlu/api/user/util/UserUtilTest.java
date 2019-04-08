package com.xunlu.api.user.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class UserUtilTest {

    @Test
    public void testGenerateDefaultNickName() {
        String nickname = UserUtil.generateDefaultNickName();
        Assert.assertTrue(nickname.startsWith(UserUtil.DEFAULT_NICK_NAME_PREFIX));
        Assert.assertEquals(UserUtil.DEFAULT_NICK_NAME_PREFIX.length() + 10, nickname.length());
        Assert.assertTrue(nickname.substring(UserUtil.DEFAULT_NICK_NAME_PREFIX.length()).matches("[0-9a-zA-Z]{10}"));
    }
}