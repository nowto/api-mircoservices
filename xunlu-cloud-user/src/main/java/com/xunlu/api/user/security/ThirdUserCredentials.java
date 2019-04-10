package com.xunlu.api.user.security;

/**
 * 代表第三方登录凭证
 * @author liweibo
 */
public class ThirdUserCredentials {
    public static final String DEFAULT_SIGNATURE_KEY = "myreindeer";

    /**
     * 签名
     */
    private String signature;


    /**
     * 签名用到的key
     */
    private String signatureKey = DEFAULT_SIGNATURE_KEY;

    public ThirdUserCredentials(String signature) {
        this.signature = signature;
    }


    public ThirdUserCredentials(String signature, String signatureKey) {
        this.signature = signature;
        this.signatureKey = signatureKey;
    }


    public String getSignature() {
        return signature;
    }

    public String getSignatureKey() {
        return signatureKey;
    }

    public void setSignatureKey(String signatureKey) {
        this.signatureKey = signatureKey == null ? DEFAULT_SIGNATURE_KEY : signatureKey;
    }
}
