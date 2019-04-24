package com.xunlu.api.common.restful.condition;

import org.springframework.util.Assert;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * 排序条件 中某个字段的排序条件.
 * 一般会包含进{@link SortCondition}中, 每个{@link SortCondition}对象代表一个完整的排序查询条件
 * @author liweibo
 */
public class Order {
    private OrderBy orderBy = OrderBy.ASC;

    private String field;

    public Order(String field) {
        Assert.notNull(field, "field不能为null");
        this.field = field;
    }

    public Order(String field, OrderBy orderBy) {
        Assert.notNull(field, "field不能为null");
        Assert.notNull(orderBy, "orderBy不能为null");
        this.field = field;
        this.orderBy = orderBy;
    }

    public enum OrderBy {
        ASC('+'), DESC('-');
        private char symbol;

        OrderBy(char symbol) {
            this.symbol = symbol;
        }

        public static OrderBy getBySymbol(char symbol) {
            if (symbol == ASC.symbol) {
                return ASC;
            }
            if (symbol == DESC.symbol) {
                return DESC;
            }
            throw new IllegalArgumentException("符号不许是'+'或'-'");
        }

        public static boolean isSymbol(char c) {
            return c == ASC.symbol || c == DESC.symbol;
        }

        public char symbol() {
            return symbol;
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                .add("orderBy=" + orderBy)
                .add("field='" + field + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return field.equals(order.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field);
    }
}
