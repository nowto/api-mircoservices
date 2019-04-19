package com.xunlu.api.common.restful.exception;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

public class ServiceExceptionTest {

    @Test
    public void getStatus() {
        TestServiceException ex = new TestServiceException(HttpStatus.NOT_FOUND, "找不到");
        Assert.assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    public void toApiResponseEntity() {
        TestServiceException ex = new TestServiceException(HttpStatus.NOT_FOUND, "找不到");
        ResponseEntity<ServiceExceptionApiError> entity = ex.toApiResponseEntity();

        Assert.assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), entity.getStatusCodeValue());

        Assert.assertEquals("找不到", entity.getBody().getMessage());
        Assert.assertEquals(ex.getClass().getName(), entity.getBody().getException());
    }

    private class TestServiceException extends ServiceException {

        public TestServiceException(HttpStatus status, String messge) {
            super(status, messge);
        }
    }
}