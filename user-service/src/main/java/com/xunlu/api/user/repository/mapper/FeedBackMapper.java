package com.xunlu.api.user.repository.mapper;

import com.xunlu.api.user.domain.FeedBack;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户反馈数据库操作类
 * @author liweibo
 */
@Mapper
public interface FeedBackMapper {

    /**
     * 添加用户反馈
     * @param feedBack 用户反馈信息
     * @return true 成功, false 失败
     */
    boolean addFeedBack(FeedBack feedBack);
}
