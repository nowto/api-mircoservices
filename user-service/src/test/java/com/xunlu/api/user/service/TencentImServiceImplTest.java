package com.xunlu.api.user.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TencentImServiceImplTest {
    private static final String IDENTIFIER = "test_username";

    @Autowired
    TencentIMService imService;

    @Test
    public void makeUserSig() {
        String genUserSig = imService.makeUserSig(IDENTIFIER);
        Assert.assertTrue(StringUtils.hasText(genUserSig));
    }

    @Test
    public void checkUserSigSuccessful() {
        String genUserSig = imService.makeUserSig(IDENTIFIER);

        boolean checkResult = imService.checkUserSig(genUserSig, IDENTIFIER);
        Assert.assertTrue(checkResult);
    }

    @Test
    public void checkUserSigFail() {
        boolean checkResult = imService.checkUserSig("foo bar bar", IDENTIFIER);
        Assert.assertFalse(checkResult);
    }

    @Test
    public void makeIdentifier() {
        String genIdentifier = imService.makeIdentifier("hello", 1234567);
        Assert.assertEquals("bdd80a90ef8959124ef8782b84f60108", genIdentifier);
    }

    @Test
    public void accountImport() {
        boolean importResult = imService.accountImport("bdd80a90ef8959124ef8782b84f60108", "tom", null);
        Assert.assertTrue(importResult);
    }

    @Test
    public void multiAccountImport() {
        boolean importResult = imService.multiAccountImport(Arrays.asList("bdd80a90ef8959124ef8782b84f60108", "ceccc4dde183695b80daf357bf353336"));
        Assert.assertTrue(importResult);
    }

}