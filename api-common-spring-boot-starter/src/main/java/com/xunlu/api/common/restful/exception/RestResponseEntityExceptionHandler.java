package com.xunlu.api.common.restful.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Optional;

/**
 * 全局的Handler（主要是Spring Controller）异常处理器
 * @author liweibo
 */
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 处理服务层级抛出的业务异常.
     * @param serviceEx 业务异常
     * @return 异常的响应体表示
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<? extends ApiError> appError(ServiceException serviceEx) {
        //logger.debug(serviceEx); 业务异常认为是可控异常，不打印日志
        return serviceEx.toApiResponseEntity();
    }

    /**
     * 托底的异常处理，当所有异常处理方法不能匹配时，执行该方法
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError systemError(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return ApiError.NON_SERVICE_EXCEPTION_API_ERROR;
    }

    /**
     * 处理单个参数校验
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(ConstraintViolationException e) {
        for (ConstraintViolation<?> s : e.getConstraintViolations()) {
            return s::getMessage;
        }
        return ApiError.UNKNOWN_PARMETER_ERROR_API_ERROR;
    }

    /**
     * 处理参数异常
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Object body = body(ex.getBindingResult());
        return handleExceptionInternal(ex, body, headers, status, request);
    }


    /**
     * 处理实体类校验
     */
    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Object body = body(ex.getBindingResult());
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    /**
     * 覆盖父类的方法，确保所有的Spring MVC框架本身的异常包含{@link ApiError}响应体
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null) {
            body = ApiError.NON_SERVICE_EXCEPTION_API_ERROR;
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Object body(BindingResult bindingResult) {
        Optional<String> firstErrorMessage = bindingResult
                .getAllErrors().stream().findFirst().map(ObjectError::getDefaultMessage);

        return firstErrorMessage.map(msg -> (ApiError)(() -> msg))
                .orElse(ApiError.UNKNOWN_PARMETER_ERROR_API_ERROR);
    }
}
