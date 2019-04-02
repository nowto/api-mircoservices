package com.xunlu.api.user.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.result.HandlerResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.naming.AuthenticationException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AuthenticationFailureController.class, secure = false)
@AutoConfigureWebClient
public class AuthenticationFailureControllerTest {
    private static final String exMsg = "errormessage";

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testHandleFailure() throws Exception {
        mockMvc.perform(post(Config.AUTHENTICATION_FAILURE_FORWARD_URL)
                    .requestAttr(WebAttributes.AUTHENTICATION_EXCEPTION, new BadCredentialsException(exMsg)))
                .andExpect(handler().methodName("handleFailure"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value(exMsg));
    }
}