package com.redparty.partyplanner.jsp.controller;

import com.redparty.partyplanner.controller.UserController;
import com.redparty.partyplanner.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@SessionAttributes("name")
@RequestMapping("jsp/users")
public class UserJspController
{
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(method = GET)
    String getAll(ModelMap modelMap) {

        String name = (String) modelMap.get("name");
        modelMap.put("users", userService.findAll());
        log.trace("getAll request");
        return "users";
    }


}
