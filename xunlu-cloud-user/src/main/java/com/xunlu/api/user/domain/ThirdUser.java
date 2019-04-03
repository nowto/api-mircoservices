package com.xunlu.api.user.domain;

import com.xunlu.api.user.infrastructure.BaseCodeEnum;

/**
 * 通过第三方登录注册的用户, 是{@link User}的一个特例.
 * 比如微信登录,微博登录等
 * @author liweibo
 */
public class ThirdUser extends User {
    /**
     * 登录类型1.微信，2.QQ，3.新浪微博
     */
    private Type type;
    private String openid;

    public ThirdUser() {}

    /**
     * 登录类型枚举 1.微信，2.QQ，3.新浪微博
     */
    public enum Type implements BaseCodeEnum<Type> {
        WEIXIN(1, "微信"),
        QQ(2, "QQ"),
        WEIBO(3, "微博")
        ;
        private int code;
        private String meaning;

        Type(int code, String meaning) {
            this.code = code;
            this.meaning = meaning;
        }

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getMeaning() {
            return meaning;
        }
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
