package com.xunlu.api.user.security;

import com.xunlu.api.user.domain.ThirdUser;
import org.apache.commons.lang3.StringUtils;

/**
 * 代表第三方用户身份
 * @author liweibo
 */
public class ThirdUserPrincipal {
    /**
     * 第三方登录方式
     */
    private ThirdUser.Type type;
    /**
     * 代表第三方用户的平台id，每个第三方用户只会具有一个openid.
     * 但微信比较特殊, 一个用户每个公众平台将会具有多个不同的openid, 这时可以使用unionid代替openid使用
     */
    private String openid;
    /**
     * 微信用户特有字段 一个微信用户只有具有一个uniionid
     */
    private String unionid;
    private String userName;
    private String imgUrl;

    /**
     * 适用于一般第三方用户
     * @param type
     * @param openid
     * @param userName
     * @param imgUrl
     */
    public ThirdUserPrincipal(ThirdUser.Type type, String openid, String userName, String imgUrl) {
        this.type = type;
        this.openid = openid;
        this.userName = userName;
        this.imgUrl = imgUrl;
    }

    /**
     * 适用于微信用户
     * @param type
     * @param openid
     * @param unionid
     * @param userName
     * @param imgUrl
     */
    public ThirdUserPrincipal(ThirdUser.Type type, String openid, String unionid, String userName, String imgUrl) {
        this.type = type;
        this.openid = openid;
        this.unionid = unionid;
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

    public String getUnionid() {
        return unionid;
    }

    /**
     * 返回第三方用户平台id.
     * 该平台该用户将只具有这个id， 不会出现多个平台id对应同一用户的情况
     * @return
     */
    public String getPlatformId() {
        if (type == ThirdUser.Type.WEIXIN && StringUtils.isNotEmpty(getUnionid())) {
            return getUnionid();
        }
        return getOpenid();
    }

    public String getUserName() {
        return userName;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
