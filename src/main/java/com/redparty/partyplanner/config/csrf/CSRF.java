package com.redparty.partyplanner.config.csrf;

public interface CSRF {

    String RESPONSE_COOKIE_NAME = "CSRF-TOKEN";
    String REQUEST_HEADER_NAME = "X-CSRF-TOKEN";

}
