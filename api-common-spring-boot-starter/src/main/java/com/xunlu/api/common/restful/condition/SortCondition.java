package com.xunlu.api.common.restful.condition;

import org.springframework.util.Assert;

import java.util.*;

/**
 * 代表了排序查询条件.
 * 一个排序查询条件可能会包含多个字段, 每个字段也都可能会是降序或升序, 但不会有重复字段.
 * 每个字段以及它的升降序使用{@link Order}表示, 也就是说 一个{@link SortCondition}由多个不同的{@link Order}组成.
 * {@link Order}是有顺序的, 排在前边的应该优先应用.
 *
 *
 * @author liweibo
 */
public class SortCondition {
    private Set<Order> orders = new LinkedHashSet<>();

    public SortCondition(Order... orders) {
        requireBothAllNotNull(orders);
        this.orders.addAll(Arrays.asList(orders));
    }

    public SortCondition() {
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SortCondition.class.getSimpleName() + "[", "]")
                .add("orders=" + orders)
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
        SortCondition sort = (SortCondition) o;
        return getOrders().equals(sort.getOrders());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrders());
    }

    public void addOrder(Order order) {
        Assert.notNull(order, "Order对象不能为null");
        orders.add(order);
    }

    public void addOrders(Order... orders) {
        requireBothAllNotNull(orders);
    }

    public List<Order> getOrders() {
        return orders.isEmpty() ? Collections.EMPTY_LIST : new ArrayList<>(orders);
    }

    private void requireBothAllNotNull(Order... orders) {
        if (orders.length == 0) {
            return;
        }
        boolean hasNullOrder = Arrays.stream(orders).anyMatch(Objects::isNull);
        Assert.isTrue(!hasNullOrder, "Order对象不能为null");
    }
}
