package com.redparty.partyplanner.controller;

import com.redparty.partyplanner.common.domain.Service;
import com.redparty.partyplanner.controller.annotation.PPRestController;
import com.redparty.partyplanner.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@PPRestController
@RequestMapping("service")
public class ServiceController extends BaseController {

    @Autowired
    private ServiceService serviceService;

    @RequestMapping("/")
    List<Service> getAll() {
        return serviceService.findAll();
    }

    @RequestMapping(value = "/{serviceId}", method = RequestMethod.GET)
    Service getById(@PathVariable("serviceId") Long serviceId) {
        return serviceService.findServiceById(serviceId);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    Service save(@RequestBody Service service) {
        return serviceService.add(service);
    }

    @RequestMapping(value = "/{serviceId}", method = RequestMethod.DELETE)
    void delete(@PathVariable("serviceId") Long serviceId) {
        serviceService.delete(serviceId);
    }

}
