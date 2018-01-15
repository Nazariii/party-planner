package com.redparty.partyplanner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redparty.partyplanner.RESTIntegrationTestBase;
import com.redparty.partyplanner.common.domain.dto.UserCreationDTO;
import com.redparty.partyplanner.controller.constant.WebConstant;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "email", password = "$2a$10$O2EO.dePSJNsu/sNEcQa5ej2leCa8B.Q95A2pQ.OOj.9bFPLHplBO")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest extends RESTIntegrationTestBase<UserController> {
    private static final String BASE = "/user/";
    private static final String USER_NAME = "email";
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get(BASE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].email", is(USER_NAME)));
    }

    @Test
    public void getByIdTest() throws Exception {
        mockMvc.perform(get(BASE + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(USER_NAME)));
    }

    @Test
    public void saveTest() throws Exception {

        UserCreationDTO user = new UserCreationDTO("name",
                "email37",
                "password",
                "password",
                "12345678", "s");
        String json = mapper.writeValueAsString(user);

        mockMvc.perform(post(BASE)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, containsString(WebConstant.USER_BASE_URL)));
    }

    @Test
    public void saveCSRFProtectionTest() throws Exception {

        UserCreationDTO user = new UserCreationDTO("name",
                "email37",
                "password",
                "password",
                "12345678", "s");
        String json = mapper.writeValueAsString(user);

        MockHttpServletResponse result = mockMvc.perform(post(BASE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn()
                    .getResponse();

        assertThat(result.getErrorMessage(), containsString("Could not verify the provided CSRF token because your session was not found."));
    }

    @Test
    public void savePasswordAndEmailValidationErrorTest() throws Exception {
        UserCreationDTO user = new UserCreationDTO("",
                "email",
                "password",
                "password1",
                "12345678", "s");
        String json = mapper.writeValueAsString(user);

        mockMvc.perform(post(BASE)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.fieldErrorResources", hasSize(3)))
                .andExpect(jsonPath("$.fieldErrorResources[*].field", containsInAnyOrder("password", "email", "name")))
                .andExpect(jsonPath("$.fieldErrorResources[*].internalFieldCode", containsInAnyOrder("password.no_match", "email.exists", "NotEmpty")));
    }

    @Test
    public void zzzDeleteTest() throws Exception {

        mockMvc.perform(delete(BASE + "/2")
                    .with(csrf()))
                .andExpect(status().isOk());
    }
}