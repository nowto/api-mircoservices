package com.xunlu.api.user.security;

import org.springframework.lang.Nullable;

/**
 * 短信验证码认证所需的Credentials, 代表一个验证手机短信验证码登录的用户除手机号之外的所有信息.
 * 在{@link SmsCodeAuthenticationToken}中使用,用于认证用户,之所以不仅仅需要phone,还需要zone,是因为短信验证需要zone.
 *
 * @author liweibo
 */
class SmsCredentials {

    /**
     * appKey
     */
    private String appKey;
    /**
     * 区号 例如 中国 86
     */
    private String zone;

    /**
     * 短信验证码
     */
    private String code;

    public SmsCredentials(@Nullable String appKey, String zone, String code) {
        this.appKey = appKey;
        this.zone = zone;
        this.code = code;
    }


    public String getZone() {
        return zone;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getCode() {
        return code;
    }
}
