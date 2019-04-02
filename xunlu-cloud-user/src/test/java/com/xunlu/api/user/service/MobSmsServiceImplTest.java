package com.xunlu.api.user.service;

import com.xunlu.api.user.security.MobSmsClient;
import com.xunlu.api.user.security.SmsClient;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MobSmsServiceImplTest {
    private MobSmsServiceImpl mobSmsService = new TestMobSmsService();

    @Test
    public void testVerify() {
        boolean ret = mobSmsService.verify(MobSmsServiceImpl.APPKEY_XINJIANG, "86", "17777777777", "2222");
        Assert.assertTrue(ret);

        ret = mobSmsService.verify(MobSmsServiceImpl.APPKEY_XINJIANG, "86", "17777777777", "1111");
        Assert.assertFalse(ret);

        try {
            ret = mobSmsService.verify(MobSmsServiceImpl.APPKEY_XINJIANG, "86", null, "1111");
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof ServiceException);
            assertEquals(MobSmsClient.CODE2MESSAGE_MAP.get("457"), e.getMessage());
        }
        Assert.assertFalse(ret);
    }

    public class TestMobSmsService extends MobSmsServiceImpl {
        @Override
        protected SmsClient newSmsClientInstance(String mobAppKey) {
            SmsClient smsClient = mock(SmsClient.class);
            try {
                when(smsClient.verify("86", "17777777777", "2222")).thenReturn(true);
                when(smsClient.verify("86", "17777777777", "1111")).thenReturn(false);
                when(smsClient.verify("86", null, "1111")).thenThrow(new SmsClient.SmsClientException(MobSmsClient.CODE2MESSAGE_MAP.get("457")));
            } catch (SmsClient.SmsClientException e) {
                e.printStackTrace();
            }
            return smsClient;
        }
    }
}