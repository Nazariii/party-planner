package com.redparty.partyplanner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redparty.partyplanner.RESTIntegrationTestBase;
import com.redparty.partyplanner.common.domain.Event;
import com.redparty.partyplanner.common.domain.User;
import com.redparty.partyplanner.common.domain.dto.EventDTO;
import com.redparty.partyplanner.repository.UserRepository;
import com.redparty.partyplanner.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "email", password = "$2a$10$O2EO.dePSJNsu/sNEcQa5ej2leCa8B.Q95A2pQ.OOj.9bFPLHplBO")
public class EventControllerTest extends RESTIntegrationTestBase<EventController> {

    private static final String BASE = "/event/";

    private static final String NAME = "one";
    private static final String EVENT_STATUS = Event.EventStatus.CLOSED.toString();
    private static final int EVENT_ID = 1;

    @Autowired
    UserService userService;

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get(BASE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(NAME)))
                .andExpect(jsonPath("$[0].id", is(EVENT_ID)))
                .andExpect(jsonPath("$[0].eventStatus", is(EVENT_STATUS)));
                //.andDo(print());
    }

    @Test
    public void getEventById() throws Exception {
        mockMvc.perform(get(BASE + EVENT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.id", is(EVENT_ID)))
                .andExpect(jsonPath("$.eventStatus", is(EVENT_STATUS)));
    }

    @Test
    public void getEventById1() throws Exception {
        mockMvc.perform(get(BASE + "11"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", containsString("Resource 'Event' with 'id' = '11' was not found")))
                .andExpect(jsonPath("$.internalCode", containsString("RESOURCE_NOT_FOUND")))
                .andExpect(jsonPath("$.fieldErrorResources", hasSize(0)));
    }

    @Test
    public void addEventTest() throws Exception {

        EventDTO event = new EventDTO(NAME, EVENT_STATUS, "1");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(event);

        mockMvc.perform(post(BASE + "add")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.eventStatus", is(EVENT_STATUS)));
    }

    @Test
    public void addEvent2Test() throws Exception {

        EventDTO event = new EventDTO(NAME, "", "1");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(event);

        mockMvc.perform(post(BASE + "add")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.eventStatus", is("HIDDEN")));
    }
}