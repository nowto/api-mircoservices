package com.xunlu.api.common.restful.condition;

import java.util.StringJoiner;

/**
 * 代表offset分页方式的条件
 * @author liweibo
 */
public class OffsetPaginationCondition extends AbstractPaginationConditon {
    public static final int DEFAULT_OFFSET = 0;

    private int offset;

    public OffsetPaginationCondition(int limit, int offset) {
        super(limit);
        this.offset = offset < 0 ? DEFAULT_OFFSET : offset;
    }

    /**
     * 获取分页条件位移, 即从哪个条目开始算起
     * @return
     */
    public int getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OffsetPaginationCondition.class.getSimpleName() + "[", "]")
                .add("limit=" + limit)
                .add("offset=" + offset)
                .toString();
    }
}
