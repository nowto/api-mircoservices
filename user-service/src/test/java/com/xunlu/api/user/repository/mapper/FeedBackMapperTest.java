package com.xunlu.api.user.repository.mapper;

import com.xunlu.api.common.CommonAutoConfiguration;
import com.xunlu.api.user.domain.FeedBack;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ImportAutoConfiguration(CommonAutoConfiguration.class)
public class FeedBackMapperTest {
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
}