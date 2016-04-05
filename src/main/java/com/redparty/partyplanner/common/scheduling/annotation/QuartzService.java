package com.redparty.partyplanner.common.scheduling.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * Indicate class as Spring service that have scheduled method
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Service
@Documented
public @interface QuartzService {
}
