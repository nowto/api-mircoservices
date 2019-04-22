package com.xunlu.api.user.resource;

import com.xunlu.api.common.CommonAutoConfiguration;
import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.service.TencentIMService;
import com.xunlu.api.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.redis.AutoConfigureDataRedis;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebMvcTest(value = UserSigResource.class, secure = false)
@AutoConfigureWebClient
@AutoConfigureDataRedis
@ImportAutoConfiguration(CommonAutoConfiguration.class)
public class UserSigResourceTest {
    private static final Integer TEST_USERID = 1;
    private static final String TEST_IDENTIFIER = "indentifier";
    private static final String TEST_USERSIG = "usersig_usersig";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TencentIMService tencentIMService;

    @MockBean
    private UserService userService;

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
        mockMvc.perform(get("/userSig/"+TEST_USERID))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(TEST_USERSIG))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getUser404() throws Exception {
        given(userService.getUser(anyInt())).willReturn(null);
        mockMvc.perform(get("/userSig/"+TEST_USERID))
                .andExpect(status().is4xxClientError())
//                .andExpect(MockMvcResultMatchers.content().string(TEST_USERSIG))
                .andDo(MockMvcResultHandlers.print());
    }
}