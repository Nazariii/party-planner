package com.redparty.partyplanner.common.exception;

import org.springframework.validation.Errors;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Errors errors) {
        super(message, errors);
    }

    public ResourceNotFoundException(String resourceName, String resourceItem, Object resourceValue) {
        this(resourceName, resourceItem, resourceValue, null);
    }

    public ResourceNotFoundException(String resourceName, String resourceItem, Object resourceValue, Errors errors) {
        super(String.format("Resource '%s' with '%s' = '%s' was not found", resourceName, resourceItem, resourceValue));
        setErrors(errors);
    }



}
