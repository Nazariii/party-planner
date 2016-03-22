package com.redparty.partyplanner.controller;

import com.redparty.partyplanner.RESTIntegrationTestBase;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class SecurityTest extends RESTIntegrationTestBase<UserController> {

    private final String userName = "email";
    private final String userPassword = "1234";
    private final String userNameWrong = "email4311";
    private final String userPasswordWrong = "123432";

    private final String CORSRequestHeader = "Origin";
    private final String CORSHeaderValue = "http://postman";
    private final String CORSHeaderWrongValue = "http://wrong.domain.com";



    @Test
    public void httpBasicAuthTest() throws Exception {
        mockMvc
                .perform(get("/user/")
                        .with(httpBasic(userName, userPassword)))
                .andExpect(status().isOk())
                .andExpect(authenticated().withUsername(userName));
    }

    @Test
    public void httpBasicAuthFailTest() throws Exception {
        mockMvc
                .perform(get("/user/")
                        .with(httpBasic(userNameWrong, userPasswordWrong)))
                .andExpect(status().isUnauthorized())
                .andExpect(unauthenticated());
    }

    @Test
    public void formLoginTest() throws Exception {
        mockMvc
                .perform(formLogin("/login")
                        .user(userName)
                        .password(userPassword))
                .andExpect(status().isFound())
                .andExpect(authenticated().withUsername(userName));
    }

    @Test
    public void formLogoutTest() throws Exception {
        mockMvc
                .perform(logout())
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = userName, password = userPassword)
    public void httpCORSTest() throws Exception {
        mockMvc
                .perform(get("/user/")
                        .header(CORSRequestHeader, CORSHeaderValue))
                .andExpect(status().isOk())
                .andExpect(authenticated());
    }

    @Test
    @WithMockUser(username = userName, password = userPassword)
    public void httpCORSFailurTest() throws Exception {
        mockMvc
                .perform(get("/user/")
                        .header(CORSRequestHeader, CORSHeaderWrongValue))
                .andExpect(status().isForbidden());
    }
}
