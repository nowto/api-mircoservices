package com.xunlu.api.user.domain;

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

    public FeedBack() {
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
}
