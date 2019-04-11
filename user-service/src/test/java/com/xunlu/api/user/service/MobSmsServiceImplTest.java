package com.xunlu.api.user.service;

import com.xunlu.api.user.security.SmsClient;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MobSmsServiceImplTest {
    public static final String CORRECT_CODE = "2222";
    public static final String WRONG_CODE = "1111";
    private MobSmsServiceImpl mobSmsService = new TestMobSmsService();

    @Test
    public void testVerify() {
        boolean ret = mobSmsService.verify(MobSmsServiceImpl.APPKEY_XINJIANG, "86", "17777777777", CORRECT_CODE);
        Assert.assertTrue(ret);

        ret = mobSmsService.verify(MobSmsServiceImpl.APPKEY_XINJIANG, "86", "17777777777", WRONG_CODE);
        Assert.assertFalse(ret);

        try {
            mobSmsService.verify("appKeyNonExists", "86", "17777777777", CORRECT_CODE);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof ServiceException);
            assertEquals("异常", e.getMessage());
        }


        try {
            mobSmsService.verify(MobSmsServiceImpl.APPKEY_XINJIANG, "86", null, WRONG_CODE);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof ServiceException);
            assertEquals("异常", e.getMessage());
        }
    }

    public class TestMobSmsService extends MobSmsServiceImpl {
        @Override
        protected SmsClient newSmsClientInstance(String mobAppKey) {
            SmsClient smsClient = mock(SmsClient.class);
            if (mobAppKey != null && MobSmsServiceImpl.appKey2MobAppKeyMap.values().contains(mobAppKey)) {
                try {
                    when(smsClient.verify("86", "17777777777", CORRECT_CODE)).thenReturn(true);
                    when(smsClient.verify("86", "17777777777", WRONG_CODE)).thenReturn(false);
                    when(smsClient.verify("86", null, WRONG_CODE)).thenThrow(new SmsClient.SmsClientException("异常"));
                } catch (SmsClient.SmsClientException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    when(smsClient.verify(anyString(), anyString(), anyString())).thenThrow(new SmsClient.SmsClientException("异常"));
                } catch (SmsClient.SmsClientException e) {
                    e.printStackTrace();
                }
            }
            return smsClient;
        }
    }
}