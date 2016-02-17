package com.redparty.partyplanner.service.impl;

import com.redparty.partyplanner.IntegrationTestBase;
import com.redparty.partyplanner.common.domain.Event;
import com.redparty.partyplanner.service.EventService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class EventServiceImplTest extends IntegrationTestBase {

    @Autowired
    private EventService eventService;

    private static final String NAME = "name of service";
    private static final Event.EventStatus EVENT_STATUS = Event.EventStatus.CLOSED;
    private static final Long USER_ID = 1L;

    @Test
    public void findEventById() throws Exception {
        Event event = eventService.findEventById(USER_ID);
        assertThat(event, notNullValue());
        assertThat(event.getName(), is("one"));
        assertThat(event.getUser().getId(), is(USER_ID));
        assertThat(event.getEventStatus(), is(EVENT_STATUS));
    }

    @Test
    public void add() throws Exception {

        Event event = eventService.add(NAME, EVENT_STATUS, USER_ID);
        assertThat(event, notNullValue());
        assertThat(event.getName(), is(NAME));
        assertThat(event.getUser().getId(), is(USER_ID));
        assertThat(event.getEventStatus(), is(EVENT_STATUS));
    }

    @Test
    public void findAll() throws Exception {
        List<Event> events = eventService.findAll();
        int size = events.size();

        eventService.add(NAME, EVENT_STATUS, USER_ID);
        events = eventService.findAll();
        assertThat("Size after adding", events, allOf(not(empty()), hasSize(++size)));
    }

    @Test
    public void delete() throws Exception {
        Event event = eventService.add(NAME, EVENT_STATUS, USER_ID);
        assertThat(event, notNullValue());
        Long eventId = event.getId();

        eventService.delete(eventId);
        assertThat("Event has been deleted", eventService.findEventById(eventId), nullValue());
    }
}