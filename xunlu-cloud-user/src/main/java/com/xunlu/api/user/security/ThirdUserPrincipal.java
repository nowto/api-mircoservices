package com.xunlu.api.user.security;

/**
 * 代表第三方用户身份
 * @author liweibo
 */
public class ThirdUserPrincipal {
    private String openid;
    private String accessToken;
    private String userName;
    private String imgUrl;
    private String unionid;

    public ThirdUserPrincipal(String openid, String accessToken, String userName, String imgUrl, String unionid) {
        this.openid = openid;
        this.accessToken = accessToken;
        this.userName = userName;
        this.imgUrl = imgUrl;
        this.unionid = unionid;
    }

    public String getOpenid() {
        return openid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUserName() {
        return userName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getUnionid() {
        return unionid;
    }
}
