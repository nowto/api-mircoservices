package com.xunlu.api.user.service;

/**
 * 短信验证服务类
 * @author liweibo
 */
public interface SmsService {
    /**
     * 校验给定的手机号的验证码是否通过
     * @param appKey 项目自定义的appKey, 方便前端调用.该后端api供前端好几个app调用, 针对不同的app, 需要使用不通的appKey进行区分.(例如发现新疆、
     *               发现台州等等)
     * @param zone 区号, 例如中国: 86
     * @param phone 手机号
     * @param code 验证码
     * @return true 代表通过, false 代表未通过
     * @throws ServiceException 验证过程中可能发生的异常, 例如手机号码为空,手机号格式不正确等等
     */
    boolean verify(String appKey, String zone, String phone, String code) throws ServiceException;
}
