package com.redparty.partyplanner.controller;

import com.redparty.partyplanner.common.exception.BaseException;
import com.redparty.partyplanner.common.exception.FieldErrorResource;
import com.redparty.partyplanner.common.exception.ObjectErrorResource;

import static com.redparty.partyplanner.common.exception.ObjectErrorResource.InternalCode;

import com.redparty.partyplanner.common.exception.ResourceNotFoundException;
import jdk.nashorn.internal.runtime.options.Option;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    /*@ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFound(RuntimeException e, WebRequest request) {
        ResourceNotFoundException ire = (ResourceNotFoundException) e;
        List<FieldErrorResource> fieldErrorResources = new ArrayList<>();


        List<FieldError> fieldErrors = ire.getErrors().getFieldErrors();

        *//**
     * ad
     *//*
        for (FieldError fieldError : fieldErrors) {
            FieldErrorResource fieldErrorResource = new FieldErrorResource();
            fieldErrorResource.setResource(fieldError.getObjectName());
            fieldErrorResource.setField(fieldError.getField());
            fieldErrorResource.setInternalFieldCode(fieldError.getInternalFieldCode());
            fieldErrorResource.setMessage(fieldError.getDefaultMessage());
            fieldErrorResources.add(fieldErrorResource);
        }

        ObjectErrorResource error = new ObjectErrorResource("InvalidRequest", ire.getMessage());
        error.setFieldErrorResources(fieldErrorResources);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, error, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }*/

    private static FieldErrorResource getFieldErrorResource(FieldError fieldError) {
        return new FieldErrorResource(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage());
    }

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
