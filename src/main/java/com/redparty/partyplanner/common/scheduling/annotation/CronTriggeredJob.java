package com.redparty.partyplanner.common.scheduling.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CronTriggeredJob {

    String name();

    String group() default "DEFAULT_GROUP";

    String cronExp();

    String timeZone() default "Europe/London";
}
