package com.xunlu.api.user.resource;

import com.xunlu.api.common.restful.exception.ResourceNotFoundServiceException;
import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.service.TencentIMService;
import com.xunlu.api.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * 腾讯云通信 UserSig资源 restful 资源类
 * @author liweibo
 * @see <a href="https://cloud.tencent.com/document/product/269/31999">腾讯云官网对登录鉴权(UserSig)的介绍</a>
 */
@RestController
@RequestMapping("/userSig")
public class UserSigResource {
    private TencentIMService tencentIMService;

    private UserService userService;

    public UserSigResource(TencentIMService tencentIMService, UserService userService) {
        this.tencentIMService = tencentIMService;
        this.userService = userService;
    }

    /**
     * 获取用户的腾讯云通信的UserSig
     * @param userId 用户主键id
     * @return 单键值对map, 键为`userSig`, 值为获取到的UserSig
     */
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, String> getUserSig(@PathVariable int userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            throw new ResourceNotFoundServiceException("查找不到该用户信息");
        }
        return Collections.singletonMap("userSig",
                tencentIMService.makeUserSig(user.getTimIdentifier())
        );
    }

}
