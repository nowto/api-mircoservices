package com.xunlu.api.common.restful.condition;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.Locale;

public class SortConditionFormatterTest {

    private final SortConditionFormatter sortConditionFormatter = new SortConditionFormatter();
    @Test
    public void parseEmptyString() throws ParseException {
        Assert.assertEquals(new SortCondition(), sortConditionFormatter.parse("", Locale.US));
    }

    @Test
    public void parseNullString() throws ParseException {
        Assert.assertEquals(new SortCondition(), sortConditionFormatter.parse(null, Locale.US));
    }

    @Test
    public void parseOrderEmptyString() throws ParseException {
        Assert.assertEquals(new SortCondition(), sortConditionFormatter.parse(",,,", Locale.US));
    }

    @Test
    public void parseNameString() throws ParseException {
        SortCondition sort = new SortCondition(new Order("name", Order.OrderBy.ASC));
        Assert.assertEquals(sort, sortConditionFormatter.parse("name", Locale.US));
    }

    @Test
    public void parseAscNameAscString() throws ParseException {
        SortCondition sort = new SortCondition(new Order("name", Order.OrderBy.ASC));
        Assert.assertEquals(sort, sortConditionFormatter.parse("+name", Locale.US));
    }

    @Test
    public void parseDescNameAscString() throws ParseException {
        SortCondition sort = new SortCondition(new Order("name", Order.OrderBy.DESC));
        Assert.assertEquals(sort, sortConditionFormatter.parse("-name", Locale.US));
    }

    @Test
    public void parseDescNameAgeString() throws ParseException {
        SortCondition sort = new SortCondition(new Order("name", Order.OrderBy.DESC), new Order("age", Order.OrderBy.ASC));
        Assert.assertEquals(sort, sortConditionFormatter.parse("-name,age", Locale.US));
    }

    @Test
    public void parseNameDescAgeString() throws ParseException {
        SortCondition sort = new SortCondition(new Order("name", Order.OrderBy.ASC), new Order("age", Order.OrderBy.DESC));
        Assert.assertEquals(sort, sortConditionFormatter.parse("name,-age", Locale.US));
    }

    @Test
    public void parseNameCommaDescAgeString() throws ParseException {
        SortCondition sort = new SortCondition(new Order("name", Order.OrderBy.ASC), new Order("age", Order.OrderBy.DESC));
        Assert.assertEquals(sort, sortConditionFormatter.parse("name,,,,-age", Locale.US));
    }

    @Test
    public void parseNameSpaceDescAgeString() throws ParseException {
        SortCondition sort = new SortCondition(new Order("name", Order.OrderBy.ASC), new Order("age", Order.OrderBy.DESC));
        Assert.assertEquals(sort, sortConditionFormatter.parse("name   ,,      , ,  -age", Locale.US));
    }

    @Test
    public void parseNameDescNameString() throws ParseException {
        SortCondition sort = new SortCondition(new Order("name", Order.OrderBy.ASC));
        Assert.assertEquals(sort, sortConditionFormatter.parse("name,-name", Locale.US));
    }

    @Test(expected = ParseException.class)
    public void parseAscString() throws ParseException {
        SortCondition sort = new SortCondition(new Order("name", Order.OrderBy.ASC), new Order("age", Order.OrderBy.DESC));
        sortConditionFormatter.parse("+", Locale.US);
    }

    @Test(expected = ParseException.class)
    public void parseDescString() throws ParseException {
        SortCondition sort = new SortCondition(new Order("name", Order.OrderBy.ASC), new Order("age", Order.OrderBy.DESC));
        sortConditionFormatter.parse("-", Locale.US);
    }
}