package com.redparty.partyplanner.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "http://localhost:9001")
public class IndexController extends BaseController {


    @RequestMapping("/welcome")
    @CrossOrigin(origins = "http://localhost:9000")
    public String index() {
        return "Hi to everybody from party planner!!!";
    }

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

}
