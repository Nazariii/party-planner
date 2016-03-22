package com.redparty.partyplanner.common.exception;

import org.springframework.validation.Errors;

public class ResourceCRUDException extends BaseException {

    public ResourceCRUDException() {
        super();
    }

    public ResourceCRUDException(String message) {
        super(message);
    }

    public ResourceCRUDException(String message, Errors errors) {
        super(message, errors);
    }

    public ResourceCRUDException(String resourceName, Object resourceValue, String operation) {
        this(resourceName, "id", resourceValue, operation, null);
    }

    public ResourceCRUDException(String resourceName, String resourceItem, Object resourceValue, String operation) {
        this(resourceName, resourceItem, resourceValue, operation, null);
    }

    public ResourceCRUDException(String resourceName, String resourceItem, Object resourceValue, String operation, Errors errors) {
        super(String.format("Resource '%s' with '%s' = '%s' was not '%s'", resourceName, resourceItem, resourceValue, operation));
        setErrors(errors);
    }
}