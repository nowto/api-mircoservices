package com.xunlu.api.user.repository.mapper;

import com.xunlu.api.common.restful.condition.OffsetPaginationCondition;
import com.xunlu.api.user.domain.FeedBack;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public class FeedBackMapperTest extends BaseMapperTest{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FeedBackMapper feedBackMapper;

    @Test
    public void testAddFeedBack() {
        FeedBack feedBack = new FeedBack();
        feedBack.setUserId(-1);
        feedBack.setContent("hello world");


        boolean ret = feedBackMapper.addFeedBack(feedBack);
        Assert.assertTrue(ret);
        Assert.assertNotNull(feedBack.getId());
    }

    @Test
    @Sql(statements = {
            "INSERT INTO xunlu.tb_user(id, create_time) values(-1, now());",
            "INSERT INTO xunlu.tb_feedback (id, user_id, user_type, content, create_time) VALUES(-1, -1, 1, '我不爱反馈', '2016-02-25 01:14:21');"})
    public void testListFeedBack() {
        List<FeedBack> ret = feedBackMapper.listFeedBack(-1, new OffsetPaginationCondition(5));

        Assert.assertTrue(ret.size() > 0);
        FeedBack feedback = ret.get(0);
        Assert.assertEquals(Integer.valueOf(-1), feedback.getId());
        Assert.assertEquals(Integer.valueOf(-1), feedback.getUserId());
        Assert.assertEquals("我不爱反馈", feedback.getContent());
        Assert.assertEquals(LocalDateTime.of(2016, 2, 25, 1, 14, 21), feedback.getCreateTime());
    }
}