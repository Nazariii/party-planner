package com.redparty.partyplanner.controller.annotation;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

import static com.redparty.partyplanner.controller.constant.WebConstant.CORS_URL;
import static com.redparty.partyplanner.controller.constant.WebConstant.CORS_URL_DEBUG;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@RestController
@CrossOrigin(origins = {CORS_URL, CORS_URL_DEBUG})
@Inherited
public @interface PPRestController {
}
