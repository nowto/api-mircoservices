package com.xunlu.api.common.restful.condition;

import org.springframework.lang.NonNull;

import java.util.StringJoiner;

/**
 * 代表seek方式分页条件.<br>
 * NOTE: 首页时 seek分页应该转化为offset条件, 使用offset方式实现分页
 * @author liweibo
 */
public class SeekPaginationCondition extends AbstractPaginationConditon {
    private Integer afterId;

    public SeekPaginationCondition(int limit, Integer afterId) {
        super(limit);
        this.afterId = afterId;
    }

    /**
     * 首页的条件
     * @param limit
     */
    public SeekPaginationCondition(int limit) {
        super(limit);
    }

    /**
     * 判断条件请求的是否是第一页.
     * seek分页方式中, 当获取第一页时(afterId为null时),其实还是需要使用offset(offset=0)分页方式.
     * 如果该方法返回true, 这是可以调用 {@link #asOffsetPaginationCondition()} 转化为offset分页条件
     * @return true 是第一页请求, false 不是
     *
     * @see #asOffsetPaginationCondition()
     */
    public boolean isFirstPage() {
        return afterId == null;
    }

    /**
     * 转化为 offset分页条件.
     * 首页时 seek分页应该转化为offset条件, 使用offset方式实现分页
     * @return offset分页条件
     * @throws IllegalStateException 如果该seek条件不是首页条件
     */
    public @NonNull OffsetPaginationCondition asOffsetPaginationCondition() throws IllegalStateException{
        if (isFirstPage()) {
            return new OffsetPaginationCondition(getLimit(), 0);
        } else {
            throw new IllegalStateException("非首页的seek分页条件不能转化为offset条件");
        }
    }

    /**
     * 获取seek条件的afterId, 即获取那个id之后条目
     * @return
     */
    public Integer getAfterId() {
        return afterId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SeekPaginationCondition.class.getSimpleName() + "[", "]")
                .add("limit=" + limit)
                .add("isFirstPage=" + isFirstPage())
                .add("afterId=" + afterId)
                .toString();
    }
}
