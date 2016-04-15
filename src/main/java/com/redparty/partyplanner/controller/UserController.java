package com.redparty.partyplanner.controller;

import com.redparty.partyplanner.common.domain.User;
import com.redparty.partyplanner.common.domain.dto.UserCreationDTO;
import com.redparty.partyplanner.common.exception.InvalidRequestException;
import com.redparty.partyplanner.controller.annotation.PPRestController;
import com.redparty.partyplanner.controller.constant.WebConstant;
import com.redparty.partyplanner.controller.util.ResponseHelper;
import com.redparty.partyplanner.controller.validator.UserCreationValidator;
import com.redparty.partyplanner.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@PPRestController
@RequestMapping(WebConstant.USER_BASE_URL)
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

    @RequestMapping(method = GET)
    List<User> getAll() {
        log.trace("getAll request");
        return userService.findAll();
    }

    @RequestMapping(value = "/{userId}", method = GET)
    User getById(@PathVariable("userId") Long userId) {
        return userService.findUserById(userId);
    }

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<User> save(@Valid @RequestBody UserCreationDTO user, BindingResult bindingResult, UriComponentsBuilder builder) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid User", bindingResult);
        }
        User newUser = userService.add(user.getEmail(), user.getPassword(), user.getName(), user.getPhone());

        return ResponseHelper.buildCreatedResponse(newUser, builder, WebConstant.USER_BASE_URL);
    }

    @RequestMapping(value = "/{userId}", method = DELETE)
    void delete(@PathVariable("userId") Long userId) {
        userService.delete(userId);
    }
}
