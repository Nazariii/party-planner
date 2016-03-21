package com.redparty.partyplanner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redparty.partyplanner.RESTIntegrationTestBase;
import com.redparty.partyplanner.common.domain.User;
import com.redparty.partyplanner.common.domain.dto.UserCreationDTO;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "email", password = "$2a$10$O2EO.dePSJNsu/sNEcQa5ej2leCa8B.Q95A2pQ.OOj.9bFPLHplBO")
public class UserControllerTest extends RESTIntegrationTestBase<UserController> {
    private static final String BASE = "/user/";
    private static final String USER_NAME = "email";
    private static final String PASSWORD = "$2a$10$O2EO.dePSJNsu/sNEcQa5ej2leCa8B.Q95A2pQ.OOj.9bFPLHplBO";

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get(BASE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].email", is(USER_NAME)));
    }

    @Test
    public void getById() throws Exception {
        mockMvc.perform(get(BASE + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(USER_NAME)));
    }

    @Test
    public void save() throws Exception {

        UserCreationDTO user = new UserCreationDTO("name",
                "email37",
                "password",
                "password",
                "12345678", "s");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);

        mockMvc.perform(post(BASE + "add")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTest() throws Exception {

        /*mockMvc.perform(delete(BASE + "/2")
                    .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());*/
    }
}