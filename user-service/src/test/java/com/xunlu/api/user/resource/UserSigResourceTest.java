package com.xunlu.api.user.resource;

import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.service.TencentIMService;
import com.xunlu.api.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = UserSigResource.class, secure = false)
public class UserSigResourceTest extends BaseResouceTest{
    private static final Integer TEST_USERID = 1;
    private static final String TEST_IDENTIFIER = "indentifier";
    private static final String TEST_USERSIG = "usersig_usersig";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestDocumentationResultHandler restDocumentation;

    @MockBean
    private TencentIMService tencentIMService;

    @MockBean
    private UserService userService;

    private static ParameterDescriptor userIdParameterDesc = parameterWithName("userId").description("用户主键").optional();

    @Before
    public void setup() {
        User user = new User();
        user.setId(TEST_USERID);
        user.setTimIdentifier(TEST_IDENTIFIER);

        given(userService.getUser(TEST_USERID)).willReturn(user);
        given(tencentIMService.makeUserSig(TEST_IDENTIFIER)).willReturn(TEST_USERSIG);
    }

    @Test
    public void getUserSig() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/userSig/{userId}", TEST_USERID))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.userSig").value(TEST_USERSIG))
            .andDo(restDocumentation.document(
                    pathParameters(userIdParameterDesc),
                    responseFields(fieldWithPath("userSig").description("生成的腾讯云通信UserSig"))
            ));
    }

    @Test
    public void getUser404() throws Exception {
        given(userService.getUser(anyInt())).willReturn(null);
        mockMvc.perform(RestDocumentationRequestBuilders.get("/userSig/{userId}", TEST_USERID))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("查找不到该用户信息"));
    }
}