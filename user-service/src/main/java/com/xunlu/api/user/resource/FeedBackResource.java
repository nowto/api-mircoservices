package com.xunlu.api.user.resource;


import com.xunlu.api.user.domain.FeedBack;
import com.xunlu.api.user.domain.FeedBackDto;
import com.xunlu.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户反馈restful资源类
 * @author liweibo
 */
@RestController
@RequestMapping("/feedbacks")
public class FeedBackResource {

    @Autowired
    private UserService userService;

    /**
     * 添加用户反馈
     * @param feedBackDto 用户反馈信息
     */
    @PostMapping
    public void addFeedback(FeedBackDto feedBackDto) {
        FeedBack feedBack = new FeedBack();
        feedBack.setUserId(feedBackDto.getUserId());
        feedBack.setContent(feedBack.getContent());
        userService.addFeedBack(feedBack);
    }
}
