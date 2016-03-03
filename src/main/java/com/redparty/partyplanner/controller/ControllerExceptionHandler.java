package com.redparty.partyplanner.controller;

import com.redparty.partyplanner.common.exception.BaseException;
import com.redparty.partyplanner.common.exception.InvalidRequestException;
import com.redparty.partyplanner.common.exception.ResourceNotFoundException;
import com.redparty.partyplanner.common.exception.helper.FieldErrorResource;
import com.redparty.partyplanner.common.exception.helper.ObjectErrorResource;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.redparty.partyplanner.common.exception.helper.ObjectErrorResource.InternalCode;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(RuntimeException e, WebRequest request) {
        return handleBasicException(e, request, InternalCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(RuntimeException e, WebRequest request) {
        return handleBasicException(e, request, InternalCode.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestException.class)
    protected ResponseEntity<Object> handleInvalidRequest(RuntimeException e, WebRequest request) {

        return handleBasicException(e, request, InternalCode.INVALID_REQUEST, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Mapper from list of FieldError objects to list of FieldErrorResource objects
     *
     * @param fieldError - list to map
     * @return mapped list
     */
    private static FieldErrorResource getFieldErrorResource(FieldError fieldError) {
        return new FieldErrorResource(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage());
    }

    /**
     * Basic logic of controllers' exceptions handling
     *
     * @param e            the exception
     * @param request      the current request
     * @param internalCode - application specific error code
     * @param httpStatus   - the selected response status
     * @return invoke method of base class
     */
    private ResponseEntity<Object> handleBasicException(RuntimeException e, WebRequest request, InternalCode internalCode, HttpStatus httpStatus) {
        BaseException ire = (BaseException) e;
        ObjectErrorResource error = new ObjectErrorResource(internalCode, ire.getMessage());

        Optional<Errors> maybeErrors = Optional.ofNullable(ire.getErrors());
        List<FieldErrorResource> fieldErrorResources = maybeErrors
                .map(Errors::getFieldErrors)
                .map(fieldErrors -> fieldErrors
                        .stream()
                        .map(ControllerExceptionHandler::getFieldErrorResource)
                        .collect(Collectors.toList()))
                .orElse(new LinkedList<>());

        error.setFieldErrorResources(fieldErrorResources);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, error, headers, httpStatus, request);
    }

}
