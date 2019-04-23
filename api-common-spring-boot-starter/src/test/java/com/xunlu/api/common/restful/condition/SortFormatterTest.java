package com.xunlu.api.common.restful.condition;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.Locale;

import static org.junit.Assert.*;

public class SortFormatterTest {

    private final SortFormatter sortFormatter = new SortFormatter();
    @Test
    public void parseEmptyString() throws ParseException {
        Assert.assertEquals(new Sort(), sortFormatter.parse("", Locale.US));
    }

    @Test
    public void parseNullString() throws ParseException {
        Assert.assertEquals(new Sort(), sortFormatter.parse(null, Locale.US));
    }

    @Test
    public void parseOrderEmptyString() throws ParseException {
        Assert.assertEquals(new Sort(), sortFormatter.parse(",,,", Locale.US));
    }

    @Test
    public void parseNameString() throws ParseException {
        Sort sort = new Sort(new Order("name", Order.OrderBy.ASC));
        Assert.assertEquals(sort, sortFormatter.parse("name", Locale.US));
    }

    @Test
    public void parseAscNameAscString() throws ParseException {
        Sort sort = new Sort(new Order("name", Order.OrderBy.ASC));
        Assert.assertEquals(sort, sortFormatter.parse("+name", Locale.US));
    }

    @Test
    public void parseDescNameAscString() throws ParseException {
        Sort sort = new Sort(new Order("name", Order.OrderBy.DESC));
        Assert.assertEquals(sort, sortFormatter.parse("-name", Locale.US));
    }

    @Test
    public void parseDescNameAgeString() throws ParseException {
        Sort sort = new Sort(new Order("name", Order.OrderBy.DESC), new Order("age", Order.OrderBy.ASC));
        Assert.assertEquals(sort, sortFormatter.parse("-name,age", Locale.US));
    }

    @Test
    public void parseNameDescAgeString() throws ParseException {
        Sort sort = new Sort(new Order("name", Order.OrderBy.ASC), new Order("age", Order.OrderBy.DESC));
        Assert.assertEquals(sort, sortFormatter.parse("name,-age", Locale.US));
    }

    @Test
    public void parseNameCommaDescAgeString() throws ParseException {
        Sort sort = new Sort(new Order("name", Order.OrderBy.ASC), new Order("age", Order.OrderBy.DESC));
        Assert.assertEquals(sort, sortFormatter.parse("name,,,,-age", Locale.US));
    }

    @Test
    public void parseNameSpaceDescAgeString() throws ParseException {
        Sort sort = new Sort(new Order("name", Order.OrderBy.ASC), new Order("age", Order.OrderBy.DESC));
        Assert.assertEquals(sort, sortFormatter.parse("name   ,,      , ,  -age", Locale.US));
    }

    @Test
    public void parseNameDescNameString() throws ParseException {
        Sort sort = new Sort(new Order("name", Order.OrderBy.ASC));
        Assert.assertEquals(sort, sortFormatter.parse("name,-name", Locale.US));
    }

    @Test(expected = ParseException.class)
    public void parseAscString() throws ParseException {
        Sort sort = new Sort(new Order("name", Order.OrderBy.ASC), new Order("age", Order.OrderBy.DESC));
        sortFormatter.parse("+", Locale.US);
    }

    @Test(expected = ParseException.class)
    public void parseDescString() throws ParseException {
        Sort sort = new Sort(new Order("name", Order.OrderBy.ASC), new Order("age", Order.OrderBy.DESC));
        sortFormatter.parse("-", Locale.US);
    }
}