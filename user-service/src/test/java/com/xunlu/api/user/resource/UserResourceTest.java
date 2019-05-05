package com.xunlu.api.user.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xunlu.api.user.domain.ThirdUser;
import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.ResponseHeadersSnippet;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.request.RequestParametersSnippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = UserResource.class, secure = false)
public class UserResourceTest extends BaseResouceTest{
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

    @Test
    public void getNormalUser() throws Exception {
        User user = new User();
        user.setId(1);
        when(userService.getUser(1)).thenReturn(user);

        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/users/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(restDocumentation.document(
                        pathParameters(
                                parameterWithName("id").description("用户主键")),
                        responseFields(
                                fieldWithPath("id").description("用户主键"))));
    }

    @Test
    public void getThirdUser() throws Exception {
        ThirdUser user = new ThirdUser();
        user.setId(1);
//        user.setOpenid("hello world");
        when(userService.getUser(1)).thenReturn(user);

        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/users/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(restDocumentation.document(
                        pathParameters(
                                parameterWithName("id").description("用户主键")),
                        responseFields(
                                fieldWithPath("id").description("用户主键"))));
    }
}