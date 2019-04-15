package com.xunlu.api.common.codeenum;

import org.springframework.util.Assert;

/**
 * 对{@link BaseCodeEnum}操作的相关工具方法
 * @author liweibo
 */
public class CodeEnumUtil {
    private CodeEnumUtil() {
        // 工具类， 拒绝实例化
    }

    /**
     * 返回枚举类codeEnumClass的枚举值中code值为code的枚举常量，如果没有，返回null
     * @param codeEnumClass 枚举类
     * @param code code
     * @param <E> 枚举类型
     * @return 枚举值为code的枚举常量，如果找不到返回null
     */
    public static <E extends Enum & BaseCodeEnum> E codeOf(Class<E> codeEnumClass, int code) {
        return codeOf(codeEnumClass, code, null);
    }

    /**
     * 返回枚举类codeEnumClass的枚举值中code值为code的枚举常量，如果没有，返回null
     * @param codeEnumClass 枚举类
     * @param code code
     * @param <E> 枚举类型
     * @return 枚举值为code的枚举常量，如果找不到返回null
     */
    public static <E extends Enum & BaseCodeEnum> E codeOf(Class<E> codeEnumClass, int code, E defaultValue) {
        Assert.notNull(codeEnumClass, "codeEnumClass不能为null");
        E[] enumConstants = codeEnumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return defaultValue;
    }
}
