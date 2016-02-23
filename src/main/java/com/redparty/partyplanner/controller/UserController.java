package com.redparty.partyplanner.controller;

import com.redparty.partyplanner.common.domain.User;
import com.redparty.partyplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@PPRestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    List<User> getAll() {
        return userService.findAll();
    }
}
