package com.redparty.partyplanner;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
}
