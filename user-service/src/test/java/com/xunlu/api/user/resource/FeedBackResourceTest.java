package com.xunlu.api.user.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xunlu.api.user.domain.FeedBack;
import com.xunlu.api.user.domain.FeedBackDto;
import com.xunlu.api.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = FeedBackResource.class, secure = false)
public class FeedBackResourceTest extends BaseResouceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestDocumentationResultHandler restDocumentation;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;

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
                .andDo(restDocumentation.document(requestFields(
                        fieldWithPath("userId").description("发送用户反馈的用户 的 用户主键"),
                        fieldWithPath("content").description("用户反馈内容")
                )));

        Mockito.verify(userService).addFeedBack(Mockito.argThat(new ArgumentMatcher<FeedBack>() {
            @Override
            public boolean matches(FeedBack argument) {
                if (argument.getUserId().equals(feedback.getUserId()) && argument.getContent().equals(feedback.getContent())) {
                    return true;
                }
                return false;
            }
        }));
    }
}