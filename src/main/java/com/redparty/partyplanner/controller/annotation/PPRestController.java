package com.redparty.partyplanner.controller.annotation;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@RestController
@CrossOrigin(origins = {"http://postman"})
@Inherited
public @interface PPRestController {
}
