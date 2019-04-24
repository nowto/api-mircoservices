package com.xunlu.api.common.restful.condition;

import org.springframework.lang.NonNull;

/**
 * 用于作为Spring Mvc的Handler Method的参数接受数据绑定, 接受RequestParameter的分页相关参数.
 *
 * <p>
 *     使用方式举例:
 *     <pre class="code">
 *         &#064;RestController
 *         &#064;RequestMapping("/employees")
 *         public class EmployeeController {
 *             &#064;Aotowired
 *             private EmployeeService employeeService;
 *
 *             &#064;GetMapping
 *             public List&#60;Employee&#62; listEmployee(PaginationParameters paginationParameters) {
 *                  employeeService.listByPage(paginationParameters.asOffsetCondition());
 *             }
 *         }
 *     </pre>
 * </p>
 * 从该对象可以: {@link #asOffsetCondition() 获取offset分页条件}、{@link #asSeekCondition() 获取seek分页条件对象}.
 * @author liweibo
 */
public class PaginationParameters {
    private Integer limit;
    private Integer offset;
    private Integer afterId;

    public PaginationParameters() {
    }

    /**
     * 获取seek分页条件对象
     * @return seek分页条件对象, 不会返回null, 从请求参数获取不到相应值时, 使用默认值
     */
    public @NonNull SeekPaginationCondition asSeekCondition() {
        return new SeekPaginationCondition(limit != null ? limit : SeekPaginationCondition.DEFAULT_LIMIT, afterId);
    }

    /**
     * 获取offset分页条件对象
     * @return offset分页条件对象, 不会返回null, 从请求参数获取不到相应值时, 使用默认值
     */
    public @NonNull OffsetPaginationCondition asOffsetCondition() {
        return new OffsetPaginationCondition(
                limit != null ? limit : OffsetPaginationCondition.DEFAULT_LIMIT,
                offset != null ? offset : OffsetPaginationCondition.DEFAULT_OFFSET
        );
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public void setAfterId(Integer afterId) {
        this.afterId = afterId;
    }
}
