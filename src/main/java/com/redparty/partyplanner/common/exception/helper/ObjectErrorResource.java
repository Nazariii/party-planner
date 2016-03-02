package com.redparty.partyplanner.common.exception.helper;

import java.util.List;

public class ObjectErrorResource {
    private InternalCode internalCode;
    private String message;
    private List<FieldErrorResource> fieldErrorResources;

    public ObjectErrorResource() {
    }

    public ObjectErrorResource(InternalCode internalCode, String message) {
        this.internalCode = internalCode;
        this.message = message;
    }

    public InternalCode getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(InternalCode internalCode) {
        this.internalCode = internalCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FieldErrorResource> getFieldErrorResources() {
        return fieldErrorResources;
    }

    public void setFieldErrorResources(List<FieldErrorResource> fieldErrorResources) {
        this.fieldErrorResources = fieldErrorResources;
    }

    public enum InternalCode {
        RESOURCE_NOT_FOUND, INTERNAL_SERVER_ERROR, INVALID_REQUEST
    }
}