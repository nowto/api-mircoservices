package com.xunlu.api.user.security;

/**
 * 手机短信验证码客户端类
 * @author liweibo
 */
public interface SmsClient {
    /**
     * 校验手机验证码是否正确. 没有抛出异常代表校验通过
     * @param zone 区号. 例如中国为 86
     * @param phone 手机号
     * @param code 验证
     * @return code验证通过返回true, 否则false
     * @throws SmsClientException 可能的异常
     */
    boolean verify(String zone, String phone, String code) throws SmsClientException;

    class SmsClientException extends Exception {
		private static final long serialVersionUID = -1787754075694400366L;
		
		public SmsClientException(String message) {
            super(message);
        }
        public SmsClientException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
