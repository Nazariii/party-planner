package com.redparty.partyplanner.controller.validator;

import com.redparty.partyplanner.common.domain.dto.UserCreationDTO;
import com.redparty.partyplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validate user creation json
 */
@Component
public class UserCreationValidator implements Validator {

    @Autowired
    private UserService userService;


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserCreationDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreationDTO userCreationDTO = (UserCreationDTO) target;
        validatePasswords(errors, userCreationDTO);
        validateEmail(errors, userCreationDTO);
    }

    private void validatePasswords(Errors errors, UserCreationDTO form) {
        if (!form.getPassword().equals(form.getPasswordRepeated())) {
            errors.rejectValue("password", "password.no_match", "Passwords do not match");
        }
    }

    private void validateEmail(Errors errors, UserCreationDTO form) {
        if (userService.existsWithEmail(form.getEmail())) {
            errors.rejectValue("email", "email.exists", "User with this email already exists");
        }
    }
}
