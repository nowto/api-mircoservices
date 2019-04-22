package com.xunlu.api.common.restful.exception;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class RestResponseEntityExceptionHandlerTest {

    RestResponseEntityExceptionHandler handler = new RestResponseEntityExceptionHandler();

    @Test
    public void appError() {
        ResponseEntity<? extends ApiError> entity = handler.appError(new ServiceException("hello"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, entity.getStatusCode());
        assertEquals("hello", entity.getBody().getMessage());
    }

    @Test
    public void systemError() {

        ApiError entity = handler.systemError(new Exception("hello"));
        assertEquals("内部服务器错误", entity.getMessage());

    }

    @Test
    public void handleValidationException() {
        Set set = new HashSet();
        set.add(new TestConstraintViolation());
        set.add(new TestConstraintViolation());
        set.add(new TestConstraintViolation());

        ConstraintViolationException e = new ConstraintViolationException("hello", set);
        ApiError entity = handler.handleValidationException(e);
        assertEquals("TestConstraintViolation", entity.getMessage());
    }

    @Test
    public void handleExceptionInternal() {

        ResponseEntity<Object> entity = handler.handleExceptionInternal(new Exception(""), null, new HttpHeaders(), HttpStatus.NOT_FOUND, mock(WebRequest.class));

        Assert.assertEquals("内部服务器错误", ((ApiError)entity.getBody()).getMessage());
    }

    public class TestConstraintViolation implements ConstraintViolation {
        @Override
        public String getMessage() {
            return "TestConstraintViolation";
        }

        @Override
        public String getMessageTemplate() {
            return null;
        }

        @Override
        public Object getRootBean() {
            return null;
        }

        @Override
        public Class getRootBeanClass() {
            return null;
        }

        @Override
        public Object getLeafBean() {
            return null;
        }

        @Override
        public Object[] getExecutableParameters() {
            return new Object[0];
        }

        @Override
        public Object getExecutableReturnValue() {
            return null;
        }

        @Override
        public Path getPropertyPath() {
            return null;
        }

        @Override
        public Object getInvalidValue() {
            return null;
        }

        @Override
        public ConstraintDescriptor<?> getConstraintDescriptor() {
            return null;
        }

        @Override
        public Object unwrap(Class type) {
            return null;
        }
    }
}