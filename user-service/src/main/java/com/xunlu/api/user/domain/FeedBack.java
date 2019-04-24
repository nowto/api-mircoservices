package com.xunlu.api.user.domain;

import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * 用户反馈
 * @author liweibo
 */
public class FeedBack {
    private Integer id;
    /**
     * 哪个用户发送的用户反馈
     */
    private Integer userId;
    /**
     * 反馈内容
     */
    private String content;

    /**
     * 创建日期
     */
    private LocalDateTime createTime;

    public FeedBack() {
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FeedBack.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userId=" + userId)
                .add("content='" + content + "'")
                .toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }
}
