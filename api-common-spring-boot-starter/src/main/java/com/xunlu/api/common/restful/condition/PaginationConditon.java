package com.xunlu.api.common.restful.condition;

/**
 * 封装了`seek-pagination`和`offset-pagination`两种分页方式的公共接口.
 *
 * @see <a href="https://www.moesif.com/blog/technical/api-design/REST-API-Design-Filtering-Sorting-and-Pagination/#offset-pagination">offset-pagination</a>
 * @see <a href="https://www.moesif.com/blog/technical/api-design/REST-API-Design-Filtering-Sorting-and-Pagination/#seek-pagination">seek-pagination</a>
 * @author liweibo
 */
public interface PaginationConditon {
    /**
     * 默认的limit大小
     */
    int DEFAULT_LIMIT = 20;

    /**

    /**
     * 获取分页条件的limit, 即一页条目的个数
     * @return
     */
    int getLimit();
}
