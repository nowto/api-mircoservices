package com.xunlu.api.common.codeenum;

import org.junit.Assert;
import org.junit.Test;

public class CodeEnumUtilTest {

    @Test(expected = IllegalArgumentException.class)
    public void codeOfShoudThrowExceptionWhenCodeEnumClassNul() {
        CodeEnumUtil.codeOf(null, 1);
    }

    @Test
    public void codeOfShoudReturnNullWhenCodeNotFound() {
        TestSexEnum notFound = CodeEnumUtil.codeOf(TestSexEnum.class, 99999);
        Assert.assertNull(notFound);
    }

    @Test
    public void codeOfShoudReturnRegularValueWhenCodeCanFound() {
        TestSexEnum found = CodeEnumUtil.codeOf(TestSexEnum.class, 1);
        Assert.assertEquals(found, TestSexEnum.MAIL);
    }

    @Test
    public void codeOfShoudReturnDefaultWhenCodeNotFound() {
        TestSexEnum found = CodeEnumUtil.codeOf(TestSexEnum.class, 99999, TestSexEnum.THIRD);
        Assert.assertEquals(TestSexEnum.THIRD, found);
    }

}