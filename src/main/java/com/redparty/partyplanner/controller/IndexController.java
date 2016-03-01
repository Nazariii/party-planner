package com.redparty.partyplanner.controller;

import com.redparty.partyplanner.controller.annotation.PPRestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@PPRestController
@CrossOrigin(origins = "http://localhost:9001")
public class IndexController extends BaseController {


    @RequestMapping("/")
    @CrossOrigin(origins = "http://localhost:9000")
    public String index() {
        return "Hy to everybody from party planner!!!";
    }

}
