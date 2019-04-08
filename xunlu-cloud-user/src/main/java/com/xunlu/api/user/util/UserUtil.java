package com.xunlu.api.user.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 有关用户{@link com.xunlu.api.user.domain.User}的实用工具方法.
 * 主要用于User类
 * @author liweibo
 */
public final class UserUtil {
    private UserUtil() {
        //工具类禁止实例化
    }

    public static final String DEFAULT_NICK_NAME_PREFIX = "Reindeer";

    private static final byte SUFFIX_LENGTH = 10;
    private static final ThreadLocalRandom random = ThreadLocalRandom.current();
    private static final char[] VALID_NICKNAME_CHARS = new char[26 * 2 + 10];

    static {
        int cursor = 0;
        for (int digit = 0; digit < 10; digit++) {
            VALID_NICKNAME_CHARS[cursor++] = Character.forDigit(digit, 10);
        }
        for (int lowerCase = 'a'; lowerCase <= 'z'; lowerCase++) {
            VALID_NICKNAME_CHARS[cursor++] = (char) lowerCase;
        }
        for (int upperCase = 'A'; upperCase <= 'Z'; upperCase++) {
            VALID_NICKNAME_CHARS[cursor++] = (char) upperCase;
        }
    }

    /**
     * 为新注册用户生成默认的昵称.
     * 规则为（Reindeer##########，Reindeer开头，跟10个(随机数字/小写字母/大写字母）.
     * 手机号用户首次登录时,会默认为其注册,其昵称将会使用此策略
     * @see com.xunlu.api.user.domain.User#newPhoneRegisterUser(String, String, String)
     * @return 生成的默认昵称
     */
    public static final String generateDefaultNickName() {
        return DEFAULT_NICK_NAME_PREFIX + randomSuffix();
    }

    private static final String randomSuffix() {
        StringBuilder nickNameSuffix = new StringBuilder(SUFFIX_LENGTH);
        for (int i = 0; i < SUFFIX_LENGTH; i++) {
            nickNameSuffix.append(nextChar());
        }
        return nickNameSuffix.toString();
    }

    private static final char nextChar() {
        return VALID_NICKNAME_CHARS[random.nextInt(VALID_NICKNAME_CHARS.length)];
    }
}
