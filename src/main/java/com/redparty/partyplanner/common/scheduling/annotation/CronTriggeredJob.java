package com.redparty.partyplanner.common.scheduling.annotation;

import java.lang.annotation.*;

/**
 * Indicate method to be scheduled with cron expression. <br><br>
 * Should be used in {@link QuartzService @QuartzService} annotated class
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CronTriggeredJob {

    String name();

    String group() default "DEFAULT_GROUP";

    String cronExp();

    String timeZone() default "Europe/London";
}
