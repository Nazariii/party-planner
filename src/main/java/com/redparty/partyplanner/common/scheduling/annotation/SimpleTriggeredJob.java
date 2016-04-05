package com.redparty.partyplanner.common.scheduling.annotation;

import java.lang.annotation.*;

/**
 * Indicate method to be scheduled with simple trigger.
 * <p>
 * Should be used in {@link QuartzService @QuartzService} annotated class
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SimpleTriggeredJob {

    String name();

    String group() default "DEFAULT_GROUP";

    int startDelay() default 0;

    int repeatInterval() default 1000;

    int repeatCount() default -1;

    int priority() default 5;

}
