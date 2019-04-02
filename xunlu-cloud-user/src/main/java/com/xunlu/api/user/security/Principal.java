package com.xunlu.api.user.security;

/**
 * 短信验证码认证所需的Principal.
 * 在{@link SmsCodeAuthenticationToken}中使用,用于认证用户,之所以不仅仅需要phone,还需要zone,是因为短信验证需要zone.
 *
 * @author liweibo
 */
class Principal {
    /**
     * 区号 例如 中国 86
     */
    private String zone;
    /**
     * 手机号
     */
    private String phone;

    public Principal(String zone, String phone) {
        this.zone = zone;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getZone() {
        return zone;
    }
}
