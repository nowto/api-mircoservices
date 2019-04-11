package com.xunlu.api.user.resource;

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
        return "hello";
    }
}
