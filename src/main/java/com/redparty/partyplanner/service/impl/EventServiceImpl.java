package com.redparty.partyplanner.service.impl;

import com.redparty.partyplanner.common.domain.Event;
import com.redparty.partyplanner.common.domain.User;
import com.redparty.partyplanner.common.exception.ResourceNotFoundException;
import com.redparty.partyplanner.repository.EventRepository;
import com.redparty.partyplanner.repository.UserRepository;
import com.redparty.partyplanner.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Event findEventById(Long id) throws ResourceNotFoundException {
        Event event = eventRepository.findOne(id);
        if (event == null) {
            throw new ResourceNotFoundException("Event","id", id);
        }
        return event;
    }

    @Override
    public Event add(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event add(String name, Event.EventStatus eventStatus, Long userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User","id", userId);
        }
        return add(new Event(name, eventStatus, user));
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        eventRepository.delete(id);
    }
}
