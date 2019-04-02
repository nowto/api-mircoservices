package com.xunlu.api.user.security;

import org.junit.Assert;
import org.junit.Test;


public class MobSmsClientTest {
    private static final String TEST_APPKEY = "1bb07cf59c173";


    private MobSmsClient mobSmsClient = new MobSmsClient(TEST_APPKEY);

    @Test
    public void testVerify() throws SmsClient.SmsClientException {
        boolean ret = mobSmsClient.verify("86", "17778014325", "3344");
        Assert.assertFalse(ret);
    }
}