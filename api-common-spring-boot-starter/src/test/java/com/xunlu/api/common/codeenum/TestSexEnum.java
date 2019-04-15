package com.xunlu.api.common.codeenum;

enum TestSexEnum implements BaseCodeEnum<TestSexEnum> {
    THIRD(0, "不男不女"),
    MAIL(1, "男"),
    FEMAIL(2, "女");

    private int code;
    private String meaning;

    TestSexEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMeaning() {
        return meaning;
    }
}
