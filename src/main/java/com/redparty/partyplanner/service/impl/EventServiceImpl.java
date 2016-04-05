package com.redparty.partyplanner.service.impl;

import com.redparty.partyplanner.common.domain.Event;
import com.redparty.partyplanner.common.domain.User;
import com.redparty.partyplanner.common.exception.ResourceCRUDException;
import com.redparty.partyplanner.common.exception.ResourceNotFoundException;
import com.redparty.partyplanner.config.constant.AppConstant;
import com.redparty.partyplanner.repository.EventRepository;
import com.redparty.partyplanner.repository.UserRepository;
import com.redparty.partyplanner.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Cacheable(AppConstant.EVENT_CACHE_NAME)
    public Event findEventById(Long id) throws ResourceNotFoundException {
        return eventRepository.findOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
    }

    @Override
    @CachePut(value = AppConstant.EVENT_CACHE_NAME, key = "#result.id")
    public Event add(Event event) {
        return eventRepository.save(event)
                .orElseThrow(() -> new ResourceCRUDException("Event", "Name", event.getName(), "Created"));
    }

    @Override
    @CachePut(value = AppConstant.EVENT_CACHE_NAME, key = "#result.id")
    public Event add(String name, Event.EventStatus eventStatus, Long userId) {
        User user = userRepository.findOne(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return add(new Event(name, eventStatus, user));
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    @CacheEvict(AppConstant.EVENT_CACHE_NAME)
    public void delete(Long id) {
        eventRepository.delete(id);
    }
}
