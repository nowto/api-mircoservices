package com.xunlu.api.common.restful.condition;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * {@link ResponseEntity}实现, 便于返回分页的资源集合数据.
 * @param <T> 资源类
 */
public class Page<T> extends ResponseEntity<List<T>> {
    public static final String TOTAL_COUNT_RESPONSE_HEADER_NAME = "X-TOTAL-COUNT";

    private int totalCount;
    /**
     * 构造一个分页的响应实体.
     * @param pageData 代表某查询条件下一页的资源,将作为{@link ResponseEntity#getBody()}输出
     * @param totalCount 代表某查询条件下该资源的总数目, 将使用自定义响应头{@link #TOTAL_COUNT_RESPONSE_HEADER_NAME X-TOTAL-COUNT}输出
     */
    public Page(List<T> pageData, int totalCount) {
        super(pageData, totalCountHeader(totalCount), HttpStatus.OK);
        this.totalCount = totalCount;
    }

    private static HttpHeaders totalCountHeader(int totalCount) {
        HttpHeaders totalCountHeader = new HttpHeaders();
        totalCountHeader.add(TOTAL_COUNT_RESPONSE_HEADER_NAME, String.valueOf(totalCount));
        return totalCountHeader;
    }

    /**
     * 构造一个集合的响应实体.(单页返回全部)
     * 主要用于不需要分页, 全部返回的情况, 此时不会输出 {@link #TOTAL_COUNT_RESPONSE_HEADER_NAME X-TOTAL-COUNT}
     * @param pageData
     */
    public Page(List<T> pageData) {
        super(pageData, HttpStatus.OK);
    }

    /**
     *获取分页数据
     * @return
     */
    public List<T> getPageData() {
        return this.getBody();
    }

    public int getTotalCount() {
        return this.totalCount;
    }
}
