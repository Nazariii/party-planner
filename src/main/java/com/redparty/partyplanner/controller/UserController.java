package com.redparty.partyplanner.controller;

import com.redparty.partyplanner.common.domain.User;
import com.redparty.partyplanner.controller.annotation.PPRestController;
import com.redparty.partyplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    User getById(@PathVariable("userId") Long userId) {
        return userService.findUserById(userId);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    User save(@RequestBody User user) {
        return userService.add(user);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    void delete(@PathVariable("userId") Long userId) {
        userService.delete(userId);
    }
}
