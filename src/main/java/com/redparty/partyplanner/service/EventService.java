package com.redparty.partyplanner.service;

import com.redparty.partyplanner.common.domain.Event;

import java.util.List;

public interface EventService {

    Event findEventById(Long id);

    Event add(Event event);

    Event add(String name, Event.EventStatus eventStatus, Long userId);

    List<Event> findAll();

    void delete(Long id);
}
