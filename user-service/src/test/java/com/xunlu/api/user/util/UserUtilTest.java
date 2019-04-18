package com.xunlu.api.user.util;

import org.junit.Assert;
import org.junit.Test;

public class UserUtilTest {

    @Test
    public void testGenerateDefaultNickName() {
        String nickname = UserUtil.generateDefaultNickName();
        Assert.assertTrue(nickname.startsWith(UserUtil.DEFAULT_NICK_NAME_PREFIX));
        Assert.assertEquals(UserUtil.DEFAULT_NICK_NAME_PREFIX.length() + 10, nickname.length());
        Assert.assertTrue(nickname.substring(UserUtil.DEFAULT_NICK_NAME_PREFIX.length()).matches("[0-9a-zA-Z]{10}"));
    }
}