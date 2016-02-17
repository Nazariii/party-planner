package com.redparty.partyplanner.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @RequestMapping("/")
    @CrossOrigin(origins = "http://localhost:9001")
    public String index() {
        return "Greetings from Spring Boot! on " ;
    }

}
