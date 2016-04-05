package com.redparty.partyplanner;


import com.redparty.partyplanner.common.scheduling.QuartzSchedulingListener;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class PartyPlannerApp {

    private static final Logger log = LoggerFactory.getLogger(PartyPlannerApp.class);

    public static void main(String[] args) {

        log.debug("+++++++++++++++++++++++++++ SPRING BOOT ++++++++++++++++++++++++++++++++");

        SpringApplication.run(PartyPlannerApp.class, args);
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Profile("Scheduling")
    public QuartzSchedulingListener quartzJobSchedulingListener(){
        return new QuartzSchedulingListener();
    }

    @Bean
    @Scope(value="prototype")
    @Profile("Scheduling")
    public SchedulerFactoryBean schedulerFactoryBean(List<Trigger> triggerFactoryBeans) {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        Trigger[] triggers = new Trigger[triggerFactoryBeans.size()];
        triggers = triggerFactoryBeans.toArray(triggers);
        scheduler.setTriggers((Trigger[]) triggers);
        return scheduler;
    }
}
