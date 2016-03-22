package com.redparty.partyplanner.controller;

import com.redparty.partyplanner.common.domain.User;
import com.redparty.partyplanner.common.domain.dto.UserCreationDTO;
import com.redparty.partyplanner.common.exception.InvalidRequestException;
import com.redparty.partyplanner.controller.annotation.PPRestController;
import com.redparty.partyplanner.controller.validator.UserCreationValidator;
import com.redparty.partyplanner.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@PPRestController
@RequestMapping("user")
public class UserController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserCreationValidator userCreationValidator;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(userCreationValidator);
    }

    @RequestMapping("/")
    List<User> getAll() {
        log.trace("getAll request");
        return userService.findAll();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    User getById(@PathVariable("userId") Long userId) {
        return userService.findUserById(userId);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    void save(@Valid @RequestBody UserCreationDTO user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid User", bindingResult);
        }
         userService.add(user.getEmail(), user.getPassword(), user.getName(), user.getPhone());
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    void delete(@PathVariable("userId") Long userId) {
        userService.delete(userId);
    }
}
