package com.redparty.partyplanner.controller;

import com.redparty.partyplanner.common.domain.Service;
import com.redparty.partyplanner.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
@RequestMapping("service")
public class ServiceController extends BaseController {

    @Autowired
    private ServiceService serviceService;

    @RequestMapping("/")
    List<Service> getAll() {
        return serviceService.findAll();
    }
}
