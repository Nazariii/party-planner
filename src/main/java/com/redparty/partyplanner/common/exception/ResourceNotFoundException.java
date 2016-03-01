package com.redparty.partyplanner.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Errors errors) {
        super(message);
        setErrors(errors);
    }

    public ResourceNotFoundException(String resourceName, String resourceItem, Object resourceValue) {
        super(String.format("Resource '%s' with '%s' = '%s' was not found", resourceName, resourceItem, resourceValue));
    }

    public ResourceNotFoundException(String resourceName, String resourceItem, Object resourceValue, Errors errors) {
        super(String.format("Resource '%s' with '%s' = '%s' was not found", resourceName, resourceItem, resourceValue));
        setErrors(errors);
    }



}
