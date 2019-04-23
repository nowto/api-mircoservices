package com.xunlu.api.user.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;

/**
 * @author liweibo
 */
public class FeedBackDto {
    private int userId;
    @NotBlank
    private String content;

    public FeedBackDto(int userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userId", userId)
                .append("content", content)
                .toString();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
