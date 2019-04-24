package com.xunlu.api.common.restful.condition;

/**
 * 分页条件抽象基类
 * @author liweibo
 */
public abstract class AbstractPaginationConditon implements PaginationConditon {

    protected int limit;

    public AbstractPaginationConditon(int limit) {
        this.limit = limit < 1 ? DEFAULT_LIMIT : limit;
    }

    @Override
    public int getLimit() {
        return limit;
    }
}
