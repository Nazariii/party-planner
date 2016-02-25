package com.redparty.partyplanner.controller;

import com.redparty.partyplanner.RESTIntegrationTestBase;
import com.redparty.partyplanner.common.domain.Event;
import org.junit.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventControllerTest extends RESTIntegrationTestBase<EventController> {

    private static final String NAME = "one";
    private static final String EVENT_STATUS = Event.EventStatus.CLOSED.toString();
    private static final int EVENT_ID = 1;

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get("/event/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(NAME))).andDo(print())
                .andExpect(jsonPath("$[0].id", is(EVENT_ID))).andDo(print())
                .andExpect(jsonPath("$[0].eventStatus", is(EVENT_STATUS)))
                .andDo(print());
    }
}