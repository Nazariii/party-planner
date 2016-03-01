package com.redparty.partyplanner.common.exception;

import org.springframework.validation.Errors;

public class InvalidRequestException extends BaseException {
    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRequestException(String message, Errors errors) {
        super(message);
        setErrors(errors);
    }
}
