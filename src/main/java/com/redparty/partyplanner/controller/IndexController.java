package com.redparty.partyplanner.controller;

import com.redparty.partyplanner.controller.annotation.PPRestController;
import com.redparty.partyplanner.controller.constant.WebConstant;
import org.springframework.web.bind.annotation.RequestMapping;

@PPRestController
@RequestMapping(WebConstant.BASE_URL)
public class IndexController extends BaseController {

    public String index() {
        return "Hy to everybody from party planner!!!";
    }

}
