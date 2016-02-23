package com.redparty.partyplanner.controller;

import com.redparty.partyplanner.common.domain.Event;
import com.redparty.partyplanner.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@PPRestController
@RequestMapping("event")
public class EventController extends BaseController{

    @Autowired
    private EventService eventService;

    @RequestMapping("/")
    List<Event> getAll() {
        return eventService.findAll();
    }
}
