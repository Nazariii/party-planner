package com.redparty.partyplanner.controller.annotation;

import com.redparty.partyplanner.controller.constant.PPURLPath;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@RestController
@CrossOrigin(origins = {PPURLPath.CORS_URL})
@Inherited
public @interface PPRestController {
}
