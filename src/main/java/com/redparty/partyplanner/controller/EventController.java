package com.redparty.partyplanner.controller;

import com.redparty.partyplanner.common.domain.Event;
import com.redparty.partyplanner.common.domain.Event.EventStatus;
import com.redparty.partyplanner.common.domain.dto.EventDTO;
import com.redparty.partyplanner.common.exception.InvalidRequestException;
import com.redparty.partyplanner.controller.annotation.PPRestController;
import com.redparty.partyplanner.controller.constant.WebConstant;
import com.redparty.partyplanner.controller.util.ResponseHelper;
import com.redparty.partyplanner.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@PPRestController
@RequestMapping(WebConstant.EVENT_BASE_URL)
public class EventController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @RequestMapping(method = RequestMethod.GET)
    List<Event> getAll() {
        return eventService.findAll();
    }

    @RequestMapping(value = "/{eventId}", method = RequestMethod.GET)
    Event getById(@PathVariable("eventId") Long eventId) {
        return eventService.findEventById(eventId);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Event> saveEvent(@Valid @RequestBody EventDTO event, BindingResult bindingResult, UriComponentsBuilder builder) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid Event", bindingResult);
        }

        EventStatus eventStatus = EventStatus.HIDDEN;
        String status = event.getEventStatus();
        if (!"".equals(status)) {
            try {
                eventStatus = EventStatus.valueOf(status);
            } catch (IllegalArgumentException e) {
                log.error("No such event status", e);
            }
        }

        Event newEvent = eventService.add(event.getName(), eventStatus, Long.valueOf(event.getUserId()));
        return ResponseHelper.buildCreatedResponse(newEvent, builder, WebConstant.USER_BASE_URL);

    }

    @RequestMapping(value = "/{eventId}", method = RequestMethod.DELETE)
    void delete(@PathVariable("eventId") Long eventId) {
        eventService.delete(eventId);
    }

}
