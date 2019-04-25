package com.xunlu.api.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * common的配置属性
 * @author liweibo
 */
@ConfigurationProperties(prefix = CommonProperties.COMMON_PREFIX)
public class CommonProperties {
    public static final String COMMON_PREFIX = "common";

    /**
     * 日期时间格式
     */
    private String dateTimePattern = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时期格式
     */
    private String datePattern = "yyyy-MM-dd";

    /**
     * 时间格式
     */
    private String timePattern = "HH:mm:ss";

    public CommonProperties() {
    }

    public String getDateTimePattern() {
        return dateTimePattern;
    }

    public void setDateTimePattern(String dateTimePattern) {
        this.dateTimePattern = dateTimePattern;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    public String getTimePattern() {
        return timePattern;
    }

    public void setTimePattern(String timePattern) {
        this.timePattern = timePattern;
    }
}
