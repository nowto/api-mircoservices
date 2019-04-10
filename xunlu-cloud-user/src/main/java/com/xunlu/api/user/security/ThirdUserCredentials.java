package com.xunlu.api.user.security;

import com.xunlu.api.user.domain.ThirdUser;

/**
 * 代表第三方登录凭证
 * @author liweibo
 */
public class ThirdUserCredentials {
    public static final String SIGNATURE_DEFAULT_KEY = "myreindeer";

    /**
     * 签名
     */
    private String signature;

    /**
     * 第三方登录方式
     */
    private ThirdUser.Type type = ThirdUser.Type.WEIXIN;


    /**
     * 签名用到的key
     */
    private String signatureKey = SIGNATURE_DEFAULT_KEY;

    public ThirdUserCredentials(String signature) {
        this.signature = signature;
    }

    public ThirdUserCredentials(String signature, ThirdUser.Type type) {
        this.signature = signature;
        this.type = type;
    }

    public ThirdUserCredentials(String signature, String signatureKey) {
        this.signature = signature;
        this.signatureKey = signatureKey;
    }

    public ThirdUserCredentials(String signature, ThirdUser.Type type, String signatureKey) {
        this.signature = signature;
        this.type = type;
        this.signatureKey = signatureKey;
    }

    public String getSignature() {
        return signature;
    }

    public ThirdUser.Type getType() {
        return type;
    }

    public String getSignatureKey() {
        return signatureKey;
    }

    public void setSignatureKey(String signatureKey) {
        this.signatureKey = signatureKey == null ? SIGNATURE_DEFAULT_KEY : signatureKey;
    }

    public void setType(ThirdUser.Type type) {
        this.type = type == null ? ThirdUser.Type.WEIXIN : type;
    }
}
