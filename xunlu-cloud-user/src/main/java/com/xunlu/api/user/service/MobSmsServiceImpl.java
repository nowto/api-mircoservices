package com.xunlu.api.user.service;

import com.xunlu.api.user.security.MobSmsClient;
import com.xunlu.api.user.security.SmsClient;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用Mob平台的 <code>SmsService</code>的实现.
 * <a href="http://wiki.mob.com/%e9%aa%8c%e8%af%81%e7%a0%81%e6%9c%8d%e5%8a%a1%e5%99%a8%e6%a0%a1%e9%aa%8c%e6%8e%a5%e5%8f%a3/">验证码服务器校验接口</a>
 * @author liweibo
 */
@Service
public class MobSmsServiceImpl implements SmsService {
    /**
     * 丽江
     */
    public static final String APPKEY_LIJIANG = "lijiang";
    /**
     * 澳门
     */
    public static final String APPKEY_MACAU = "macau";
    /**
     * 盘锦
     */
    public static final String APPKEY_PANJIN = "panjin";
    /**
     * 新疆
     */
    public static final String APPKEY_XINJIANG = "xinjiang";

    public static final String DEFAULT_MOB_APPkEY = "1164e90a15fa8";

    private static final Map<String, SmsClient> appKey2smsClientMap = new HashMap<>();
    private static final Map<String, String> appKey2MobAppKeyMap = new HashMap<>();
    static {
        appKey2MobAppKeyMap.put(APPKEY_LIJIANG, "1bb07cf59c173");
        appKey2MobAppKeyMap.put(APPKEY_MACAU, "1e84a5d2a2ef0");
        appKey2MobAppKeyMap.put(APPKEY_PANJIN, "2067c006d1348");
        appKey2MobAppKeyMap.put(APPKEY_XINJIANG, "29cb3fbfdb19e");
    }

    public MobSmsServiceImpl() {
    }


    /**
     * 验证手机验证码是否正确
     * @param appKey [注意]:此appKey是api业务上的appKey, 并非调用Mob平台的appKey, 但是与Mob平台的appKey有一一对应关系, 在调用Mob平台时,
     *               会将此appkey转化为Mob平台的appKey.之所以做这一转化, 是因为Mob平台的appKey(比如'1bb07cf59c173')难记且不易读.
     *               允许的appKey值, 定义成了MobSmsServiceImpl的几个常量():
     *               {@link #APPKEY_LIJIANG}
     *               {@link #APPKEY_MACAU}
     *               {@link #APPKEY_PANJIN}
     *               {@link #APPKEY_XINJIANG}
     *
     *               当不是以上枚举值时,将使用{@link #DEFAULT_MOB_APPkEY}作为请求Mob的appKey,获取验证结果
     *
     *
     * @param zone 区号, 例如中国: 86
     * @param phone 手机号
     * @param code 验证码
     * @return true 验证通过, false验证失败
     * @throws ServiceException
     * @see <a href="http://wiki.mob.com/%e9%aa%8c%e8%af%81%e7%a0%81%e6%9c%8d%e5%8a%a1%e5%99%a8%e6%a0%a1%e9%aa%8c%e6%8e%a5%e5%8f%a3/">验证码服务器校验接口</a>
     */
    @Override
    public boolean verify(@Nullable String appKey, String zone, String phone, String code) throws ServiceException {
        SmsClient smsClient = getSuitableSmsClient(appKey);
        try {
            return smsClient.verify(zone, phone, code);
        } catch (SmsClient.SmsClientException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private SmsClient getSuitableSmsClient(String appKey) {
        return appKey2smsClientMap.computeIfAbsent(appKey,
                key -> newSmsClientInstance(appKey2MobAppKeyMap.getOrDefault(key, DEFAULT_MOB_APPkEY))
        );
    }

    /**
     * method for unit test
     * @param mobAppKey
     * @return
     */
    protected SmsClient newSmsClientInstance(String mobAppKey) {
        return new MobSmsClient(mobAppKey);
    }
}
