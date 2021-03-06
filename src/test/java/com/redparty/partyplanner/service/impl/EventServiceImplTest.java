package com.redparty.partyplanner.service.impl;

import com.redparty.partyplanner.common.domain.Event;
import com.redparty.partyplanner.common.exception.ResourceNotFoundException;
import com.redparty.partyplanner.config.constant.AppConstant;
import com.redparty.partyplanner.service.EventService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("testing")
@ExtendWith(SpringExtension.class)
public class EventServiceImplTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private CacheManager cacheManager;

    private static final String NAME = "name of service";
    private static final Event.EventStatus EVENT_STATUS = Event.EventStatus.CLOSED;
    private static final Long USER_ID = 1L;
    private static final Long EVENT_ID = 1L;

    @Test
    public void findEventById() throws Exception {
        Event event = eventService.findEventById(EVENT_ID);
        assertThat(event, notNullValue());
        assertThat(event.getName(), is("one"));
        assertThat(event.getUser().getId(), is(USER_ID));
        assertThat(event.getEventStatus(), is(EVENT_STATUS));
    }

    @Test
    public void testCacheWhenFindById() throws Exception {
        Event event = eventService.findEventById(EVENT_ID);
        Event cachedEvent = eventService.findEventById(EVENT_ID);
        assertThat(cachedEvent, sameInstance(event));
        assertThat(cacheManager.getCache(AppConstant.EVENT_CACHE_NAME).get(EVENT_ID), notNullValue());
    }

    @Test
    public void testAddingEvent() throws Exception {

        Event event = eventService.add(NAME, EVENT_STATUS, USER_ID);
        assertThat(event, notNullValue());
        assertThat(event.getName(), is(NAME));
        assertThat(event.getUser().getId(), is(USER_ID));
        assertThat(event.getEventStatus(), is(EVENT_STATUS));

    }

    @Test
    public void testAddingEventCache() throws Exception {

        Event event = eventService.add(NAME, EVENT_STATUS, USER_ID);
        Event cachedEvent = eventService.findEventById(event.getId());
        assertThat(cachedEvent, sameInstance(event));
        assertThat(cacheManager.getCache(AppConstant.EVENT_CACHE_NAME).get(event.getId()), notNullValue());
    }

    @Test
    public void testFindAll() throws Exception {
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
        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> eventService.findEventById(eventId));
        MatcherAssert.assertThat(exception.getMessage(), Matchers.is("Resource 'Event' with 'id' = '" + eventId + "' was not found"));
    }

    @Test
    public void testDeleteWithCleaningCache() {
        Event event = eventService.add(NAME, EVENT_STATUS, USER_ID);
        Long eventId = event.getId();

        eventService.delete(eventId);
        assertThat(cacheManager.getCache(AppConstant.EVENT_CACHE_NAME).get(event.getId()), nullValue());
    }
}