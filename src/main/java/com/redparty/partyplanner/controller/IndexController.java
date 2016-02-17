package com.redparty.partyplanner.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:9001")
public class IndexController extends BaseController {


    @RequestMapping("/")
    @CrossOrigin(origins = "http://localhost:9000")
    public String index() {
        return "Hy to everybody from party planner!!!";
    }

}
