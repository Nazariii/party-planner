package com.redparty.partyplanner.common.exception;

/**
 * Class for describing errors in object's field
 */
public class FieldErrorResource {
    private String resource;
    private String field;
    private String internalFieldCode;
    private String message;

    public FieldErrorResource() {
    }

    public FieldErrorResource(String resource, String field, String internalFieldCode, String message) {
        this.resource = resource;
        this.field = field;
        this.internalFieldCode = internalFieldCode;
        this.message = message;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getInternalFieldCode() {
        return internalFieldCode;
    }

    public void setInternalFieldCode(String internalFieldCode) {
        this.internalFieldCode = internalFieldCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
