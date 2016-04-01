package com.redparty.partyplanner.controller;

import com.redparty.partyplanner.common.domain.Service;
import com.redparty.partyplanner.common.domain.dto.ServiceDTO;
import com.redparty.partyplanner.common.exception.InvalidRequestException;
import com.redparty.partyplanner.controller.annotation.PPRestController;
import com.redparty.partyplanner.controller.constant.PPURLPath;
import com.redparty.partyplanner.controller.util.ResponseHelper;
import com.redparty.partyplanner.service.ServiceService;
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
@RequestMapping(PPURLPath.SERVICE_BASE_URL)
public class ServiceController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private ServiceService serviceService;

    @RequestMapping(method = RequestMethod.GET)
    List<Service> getAll() {
        return serviceService.findAll();
    }

    @RequestMapping(value = "/{serviceId}", method = RequestMethod.GET)
    Service getById(@PathVariable("serviceId") Long serviceId) {
        return serviceService.findServiceById(serviceId);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Service> save(@Valid @RequestBody ServiceDTO service, BindingResult bindingResult, UriComponentsBuilder builder) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid Service", bindingResult);
        }
        Service newService = serviceService.add(service.getName(), Long.valueOf(service.getUserId()));
        return ResponseHelper.buildCreatedResponce(newService, builder, PPURLPath.USER_BASE_URL);

    }

    @RequestMapping(value = "/{serviceId}", method = RequestMethod.DELETE)
    void delete(@PathVariable("serviceId") Long serviceId) {
        serviceService.delete(serviceId);
    }

}
