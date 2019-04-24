package com.xunlu.api.user.repository.mapper;

import com.xunlu.api.common.restful.condition.OffsetPaginationCondition;
import com.xunlu.api.user.domain.FeedBack;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

import java.util.List;

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

    /**
     * 获取用户id为{@code userId}的用户反馈信息, 分页获取
     * @param userId 获取该id用户的用户反馈
     * @param pageCondition 分页条件
     * @return 获取到的用户反馈信息
     */
    @NonNull List<FeedBack> listFeedBack(@NonNull @Param("userId") int userId,
                                         @NonNull @Param("pageCondition") OffsetPaginationCondition pageCondition);

    /**
     * 获取用户id为{@code userId}的用户的用户反馈数量
     * @param userId 获取该id用户的用户反馈数量
     * @return 用户反馈数量
     */
    int getFeedBackCount(@NonNull @Param("userId") int userId);
}
