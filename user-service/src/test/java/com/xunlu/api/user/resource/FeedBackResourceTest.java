package com.xunlu.api.user.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xunlu.api.common.restful.condition.OffsetPaginationCondition;
import com.xunlu.api.common.restful.condition.Page;
import com.xunlu.api.user.domain.FeedBack;
import com.xunlu.api.user.domain.FeedBackDto;
import com.xunlu.api.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.ResponseHeadersSnippet;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.RequestParametersSnippet;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doAnswer;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = FeedBackResource.class, secure = false)
public class FeedBackResourceTest extends BaseResouceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestDocumentationResultHandler restDocumentation;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RequestParametersSnippet offsetPageParams;

    @Autowired
    private ResponseHeadersSnippet totalCountHeader;

    @MockBean
    UserService userService;

    private FieldDescriptor[] postFeedbackFields = {
            fieldWithPath("userId").description("发送用户反馈的用户 的 用户主键"),
            fieldWithPath("content").description("用户反馈内容")
    };

    @Before
    public void setup() {
    }

    @Test
    public void addFeedback() throws Exception {
        FeedBackDto feedback = new FeedBackDto();
        feedback.setUserId(4444);
        feedback.setContent("content");

        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(feedback)))
                .andExpect(status().isOk())
                .andDo(restDocumentation.document(requestFields(postFeedbackFields)));

        Mockito.verify(userService).addFeedBack(Mockito.argThat(argument -> argument.getUserId().equals(feedback.getUserId()) && argument.getContent().equals(feedback.getContent())));
    }

    @Test
    public void getFeedbacks() throws Exception {
        doAnswer(invocation -> {
            FeedBack o = new FeedBack();
            o.setId(1);
            o.setUserId(1);
            o.setContent("hello");
            o.setCreateTime(LocalDateTime.of(2019, 1, 1, 1, 1, 1));
            return new Page<>(Collections.singletonList(o), 3);
        }).when(userService).listFeedBack(1, new OffsetPaginationCondition());


        List<FieldDescriptor> getFeedbackFields = new ArrayList<>();
        getFeedbackFields.add(fieldWithPath("id").description("反馈主键"));
        getFeedbackFields.add(fieldWithPath("createTime").description("创建时间"));
        getFeedbackFields.addAll(Arrays.asList(postFeedbackFields));

        mockMvc.perform(get("/feedbacks")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(header().string("X-TOTAL-COUNT", "3"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[*]").isArray())
                .andExpect(jsonPath("$.[*]").isNotEmpty())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].content").value("hello"))
                .andExpect(jsonPath("$.[0].createTime").value("2019-01-01 01:01:01"))
                .andExpect(jsonPath("$.[0].userId").value(1))
                .andDo(restDocumentation.document(
                        offsetPageParams.and(parameterWithName("userId").description("用户主键")),
                        totalCountHeader,
                        responseFields(
                                fieldWithPath("[]").description("用户反馈数组"))
                                .andWithPrefix("[].", getFeedbackFields)
                ));
    }
}