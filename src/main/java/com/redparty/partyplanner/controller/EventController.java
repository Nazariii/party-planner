package com.redparty.partyplanner.controller;

import com.redparty.partyplanner.common.domain.Event;
import com.redparty.partyplanner.common.exception.InvalidRequestException;
import com.redparty.partyplanner.controller.annotation.PPRestController;
import com.redparty.partyplanner.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@PPRestController
@RequestMapping("event")
public class EventController extends BaseController {

    @Autowired
    private EventService eventService;

    @RequestMapping({"/", ""})
    List<Event> getAll() {
        return eventService.findAll();
    }

    @RequestMapping(value = "/{eventId}", method = RequestMethod.GET)
    Event getById(@PathVariable("eventId") Long eventId) {
        return eventService.findEventById(eventId);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    Event save(@Valid @RequestBody Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid Event", bindingResult);
        }
        return eventService.add(event);
    }

    @RequestMapping(value = "/{eventId}", method = RequestMethod.DELETE)
    void delete(@PathVariable("eventId") Long eventId) {
        eventService.delete(eventId);
    }

}
