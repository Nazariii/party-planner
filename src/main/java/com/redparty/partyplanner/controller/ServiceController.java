package com.redparty.partyplanner.controller;

import com.redparty.partyplanner.common.domain.Service;
import com.redparty.partyplanner.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
