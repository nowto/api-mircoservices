package com.xunlu.api.user.resource;

import com.xunlu.api.common.restful.exception.ResourceNotFoundServiceException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liweibo
 */
@RestController
public class UserResource {

    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        throw new ResourceNotFoundServiceException("资源不存在啊");
    }
}
