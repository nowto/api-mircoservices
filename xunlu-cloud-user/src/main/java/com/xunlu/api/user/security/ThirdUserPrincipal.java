package com.xunlu.api.user.security;

import com.xunlu.api.user.domain.ThirdUser;

/**
 * 代表第三方用户身份
 * @author liweibo
 */
public class ThirdUserPrincipal {
    /**
     * 第三方登录方式
     */
    private ThirdUser.Type type;
    private String openid;
    private String userName;
    private String imgUrl;

    public ThirdUserPrincipal(ThirdUser.Type type, String openid, String userName, String imgUrl) {
        this.type = type;
        this.openid = openid;
        this.userName = userName;
        this.imgUrl = imgUrl;
    }

    public ThirdUser.Type getType() {
        return type;
    }

    public void setType(ThirdUser.Type type) {
        this.type = type == null ? ThirdUser.Type.WEIXIN : type;
    }

    public String getOpenid() {
        return openid;
    }

    public String getUserName() {
        return userName;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
