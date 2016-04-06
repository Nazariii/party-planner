package com.redparty.partyplanner.service;

import com.redparty.partyplanner.common.domain.Event;
import com.redparty.partyplanner.config.constant.AppConstant;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface EventService {

    @Cacheable(AppConstant.EVENT_CACHE_NAME)
    Event findEventById(Long id);

    @CachePut(value = AppConstant.EVENT_CACHE_NAME, key = "#result.id")
    Event add(Event event);

    @CachePut(value = AppConstant.EVENT_CACHE_NAME, key = "#result.id")
    Event add(String name, Event.EventStatus eventStatus, Long userId);

    List<Event> findAll();

    @CacheEvict(AppConstant.EVENT_CACHE_NAME)
    void delete(Long id);
}
