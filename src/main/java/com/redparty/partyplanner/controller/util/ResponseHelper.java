package com.redparty.partyplanner.controller.util;

import com.redparty.partyplanner.common.domain.BaseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Helper class for responses creation
 */
public class ResponseHelper {

    /**
     * Build response with created status and location header of created element
     *
     * @param response    response object
     * @param builder     url builder
     * @param pathSegment segments of path to created object (should not contain any slashes)
     * @param <T>         response object type should be of BaseEntity type
     * @return response entity
     */
    public static <T extends BaseEntity> ResponseEntity<T> buildCreatedResponse(T response, UriComponentsBuilder builder, String... pathSegment) {

        HttpHeaders headers = new HttpHeaders();
        URI locationUri =
                builder.pathSegment(pathSegment)
                        .path(String.valueOf(response.getId()))
                        .build()
                        .toUri();
        headers.setLocation(locationUri);
        return new ResponseEntity<>(
                response, headers, HttpStatus.CREATED);
    }
}
