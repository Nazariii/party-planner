package com.redparty.partyplanner;


import com.redparty.partyplanner.common.scheduling.QuartzSchedulingListener;
import org.quartz.CronTrigger;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
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

        ApplicationContext ctx = SpringApplication.run(PartyPlannerApp.class, args);
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public QuartzSchedulingListener quartJobSchedulingListener(){
        return new QuartzSchedulingListener();
    }

    @Bean//(name="scheduler")
    @Scope(value="prototype")
    public SchedulerFactoryBean schedulerFactoryBean(List<CronTrigger> triggerFactoryBeans) {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        CronTrigger[] cronTriggers = new CronTrigger[triggerFactoryBeans.size()];
        cronTriggers = triggerFactoryBeans.toArray(cronTriggers);
        scheduler.setTriggers((Trigger[]) cronTriggers);
        return scheduler;
    }
}
