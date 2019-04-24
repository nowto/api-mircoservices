package com.xunlu.api.common.restful.condition;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(PaginationParametersTest.Controller.class)
@ContextConfiguration(classes = PaginationParametersTest.Config.class)
public class PaginationParametersTest {

    @Autowired
    private MockMvc mockMvc;

    private static PaginationParameters paginationParameters;
    private static OffsetPaginationCondition offsetPaginationCondition;
    private static SeekPaginationCondition seekPaginationCondition;

    @Test
    public void testOffsetDefaultValue() throws Exception {
        mockMvc.perform(get("/offset"));
        Assert.assertEquals(OffsetPaginationCondition.DEFAULT_LIMIT, offsetPaginationCondition.getLimit());
        Assert.assertEquals(OffsetPaginationCondition.DEFAULT_OFFSET, offsetPaginationCondition.getOffset());
    }

    @Test
    public void testOffsetNonDefaultValue() throws Exception {
        mockMvc.perform(get("/offset?limit=88&offset=22"));
        Assert.assertEquals(88, offsetPaginationCondition.getLimit());
        Assert.assertEquals(22, offsetPaginationCondition.getOffset());
    }

    @Test
    public void testOffsetOnlyLimit() throws Exception {
        mockMvc.perform(get("/offset?limit=88"));
        Assert.assertEquals(88, offsetPaginationCondition.getLimit());
        Assert.assertEquals(OffsetPaginationCondition.DEFAULT_OFFSET, offsetPaginationCondition.getOffset());
    }

    @Test
    public void testOffsetOnlyOffset() throws Exception {
        mockMvc.perform(get("/offset?offset=22"));
        Assert.assertEquals(OffsetPaginationCondition.DEFAULT_LIMIT, offsetPaginationCondition.getLimit());
        Assert.assertEquals(22, offsetPaginationCondition.getOffset());
    }


    @Test
    public void testSeekDefaultValue() throws Exception {
        mockMvc.perform(get("/seek"));
        Assert.assertEquals(seekPaginationCondition.DEFAULT_LIMIT, seekPaginationCondition.getLimit());
        Assert.assertNull(seekPaginationCondition.getAfterId());

        Assert.assertTrue(seekPaginationCondition.isFirstPage());
        offsetPaginationCondition = seekPaginationCondition.asOffsetPaginationCondition();
        Assert.assertEquals(OffsetPaginationCondition.DEFAULT_LIMIT, offsetPaginationCondition.getLimit());
        Assert.assertEquals(0, offsetPaginationCondition.getOffset());
    }

    @Test
    public void testSeekNonDefaultValue() throws Exception {
        mockMvc.perform(get("/seek?limit=88&afterId=22"));
        Assert.assertEquals(88, seekPaginationCondition.getLimit());
        Assert.assertEquals(22, seekPaginationCondition.getAfterId().intValue());

        Assert.assertFalse(seekPaginationCondition.isFirstPage());
        try {
            offsetPaginationCondition = seekPaginationCondition.asOffsetPaginationCondition();
            Assert.fail("应该排除IllegalStateException异常");
        } catch (IllegalStateException e) {}
    }

    @Test
    public void testSeekOnlyLimit() throws Exception {
        mockMvc.perform(get("/seek?limit=88"));
        Assert.assertEquals(88, seekPaginationCondition.getLimit());
        Assert.assertNull(seekPaginationCondition.getAfterId());

        Assert.assertTrue(seekPaginationCondition.isFirstPage());
        offsetPaginationCondition = seekPaginationCondition.asOffsetPaginationCondition();
        Assert.assertEquals(88, offsetPaginationCondition.getLimit());
        Assert.assertEquals(0, offsetPaginationCondition.getOffset());
    }

    @Test
    public void testSeekOnlyAfterId() throws Exception {
        mockMvc.perform(get("/seek?afterId=22"));
        Assert.assertEquals(SeekPaginationCondition.DEFAULT_LIMIT, seekPaginationCondition.getLimit());
        Assert.assertEquals(22, seekPaginationCondition.getAfterId().intValue());

        Assert.assertFalse(seekPaginationCondition.isFirstPage());
        try {
            offsetPaginationCondition = seekPaginationCondition.asOffsetPaginationCondition();
            Assert.fail("应该排除IllegalStateException异常");
        } catch (IllegalStateException e) {}
    }


    @Configuration
    @Import(Controller.class)
    public static class Config{}

    @RestController
    public static class Controller {
        @GetMapping("/offset")
        public OffsetPaginationCondition offset(PaginationParameters paginationParameters) {
            PaginationParametersTest.paginationParameters = paginationParameters;

            OffsetPaginationCondition offsetPaginationCondition = paginationParameters.asOffsetCondition();
            PaginationParametersTest.offsetPaginationCondition = offsetPaginationCondition;
            return offsetPaginationCondition;
        }

        @GetMapping
        public SeekPaginationCondition seek(PaginationParameters paginationParameters) {
            PaginationParametersTest.paginationParameters = paginationParameters;


            SeekPaginationCondition seekPaginationCondition = paginationParameters.asSeekCondition();
            PaginationParametersTest.seekPaginationCondition = seekPaginationCondition;
            return seekPaginationCondition;
        }
    }
}