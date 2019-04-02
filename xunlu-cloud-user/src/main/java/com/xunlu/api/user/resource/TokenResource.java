package com.xunlu.api.user.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Token Restful Resource 端点类
 * @author liweibo
 */
@Controller
public class TokenResource {

    @PostMapping("/token")
    @ResponseBody
    public String getToken(GrantType grantType) {
        return grantType == null ? "null" : grantType.toString();
    }

    public enum GrantType {
        PHONE, THIRD_USER;
    }
}
