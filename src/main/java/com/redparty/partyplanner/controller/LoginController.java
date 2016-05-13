package com.redparty.partyplanner.controller;

import com.redparty.partyplanner.common.domain.User;
import com.redparty.partyplanner.common.domain.dto.UserSecurityDTO;
import com.redparty.partyplanner.controller.annotation.PPRestController;
import com.redparty.partyplanner.controller.constant.WebConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@PPRestController
@RequestMapping(WebConstant.LOGIN_BASE_URL)
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<User> getLoggedInUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            UserSecurityDTO userSecurityDTO = (UserSecurityDTO) authentication.getPrincipal();
            return new ResponseEntity<>(userSecurityDTO.getUser(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<User> getLoggedInUser() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
