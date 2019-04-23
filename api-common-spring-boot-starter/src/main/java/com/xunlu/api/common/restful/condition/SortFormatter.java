package com.xunlu.api.common.restful.condition;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Spring {@link Formatter} 实现. 用于将 restful 排序 请求参数 转换为 {@link Sort} 条件对象.
 *
 * text语法: 多个字段使用英文逗号','分隔, 字段前附加符号代表顺序: '+', 升序; '-', 降序. 默认为升序.
 *<br>
 * example:<br>
 * <code>
 * `/users?sort=name` 按name升序排序<br>
 *
 * `/users?sort=+name` 按name升序排序<br>
 *
 * `/users?sort=-name` 按name降序排序<br>
 *
 * `/users?sort=name,-age`|`/users?sort=name&sort=-age` 首先按name升序排序，name一样时按age降序排序<br>
 *  相同名称的字段多次出现,以第一次出现为准 例如 '+name,+age,-name', 则取 '+name'
 * </code>
 * @author liweibo
 */
public class SortFormatter implements Formatter<Sort> {
    @Override
    public Sort parse(String text, Locale locale) throws ParseException {
        if (text == null) {
            return new Sort();
        }
        StringTokenizer tokenizer = new StringTokenizer(text, ",");
        Sort sort = new Sort();
        while (tokenizer.hasMoreTokens()) {
            String orderStr = tokenizer.nextToken();
            if (!StringUtils.hasText(orderStr)) {
                continue;
            }
            orderStr = orderStr.trim();
            orderStr = ensureWithSymbol(orderStr);
            if (orderStr.length() < 2) {
                throw new ParseException("每个排序条件必须具有字段名称", -1);
            }
            char symbol = getSymbol(orderStr);
            String field = getField(orderStr);
            sort.addOrder(new Order(field, Order.OrderBy.getBySymbol(symbol)));
        }
        return sort;
    }

    private String ensureWithSymbol(String orderStr) {
        char firstIndexChar = orderStr.charAt(0);
        return Order.OrderBy.isSymbol(firstIndexChar) ?
                orderStr
                : Order.OrderBy.ASC.symbol() + orderStr;
    }


    private char getSymbol(String orderStr) {
        return orderStr.charAt(0);
    }

    private String getField(String orderStr) {
        return orderStr.substring(1);
    }

    @Override
    public String print(Sort object, Locale locale) {
        throw new UnsupportedOperationException();
    }


}
