package com.xunlu.api.user.domain;

import com.xunlu.api.common.codeenum.BaseCodeEnum;

/**
 * 第三方登录用户中,微信用户比较特殊,作一ThirdUser的子类应对.
 * 当为微信用户时,tb_third_user表的openid字段存储的并不一定是openid,而是平台id(以unionid优先,unionid不存在时才使用openid),该类能获取到
 * 最原始的openid
 * @author liweibo
 */
public class WeixinThirdUser extends ThirdUser {
    /**
     * 微信登录用户原始的openid.
     * tb_third_user表的openid字段存储的并不一定是openid,而是平台id(以unionid优先,unionid不存在时才使用openid),这里是原始的openid
     */
    private String originalOpenid;

    private Client client = Client.APP;

    {
        //定死了,类型是微信
        super.setType(Type.WEIXIN);
    }

    public WeixinThirdUser() {}

    public WeixinThirdUser(String originalOpenid) {
        this.originalOpenid = originalOpenid;
    }

    @Override
    public void setType(Type type) {
        throw new UnsupportedOperationException("定死了, 类型是微信");
    }

    public void setOriginalOpenid(String originalOpenid) {
        this.originalOpenid = originalOpenid;
    }

    public String getOriginalOpenid() {
        return originalOpenid;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public enum Client implements BaseCodeEnum<Client> {
        APP(1, "app");

        private int code;
        private String meaning;

        Client(int code, String meaning) {
            this.code = code;
            this.meaning = meaning;
        }

        @Override
        public int getCode() {
            return 0;
        }

        @Override
        public String getMeaning() {
            return null;
        }
    }
}
