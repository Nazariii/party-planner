package com.redparty.partyplanner.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.redparty.partyplanner.RESTIntegrationTestBase;
import org.junit.Test;


public class SecurityTest extends RESTIntegrationTestBase<UserController> {

    private final String userName = "email";
    private final String userPassword = "1234";

    @Test
    public void httpBasicAuthTest() throws Exception {
        mockMvc
                .perform(get("/user/").with(httpBasic(userName, userPassword)))
                .andExpect(status().isOk())
                .andExpect(authenticated().withUsername(userName));
    }

    @Test
    public void formLoginTest() throws Exception {
        mockMvc
                .perform(formLogin("/login").user(userName).password(userPassword))
                .andExpect(status().isFound())
                .andExpect(authenticated().withUsername(userName));
    }

    @Test
    public void formLogoutTest() throws Exception {
        mockMvc
                .perform(logout())
                .andExpect(status().isFound());
    }
}
