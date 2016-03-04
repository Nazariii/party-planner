package com.redparty.partyplanner.config;

import com.redparty.partyplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.BaseDigestPasswordEncoder;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                    .withUser("naz1").password("1234").roles("USER")
                    .and()
                    .withUser("admin").password("1234").roles("USER", "ADMIN") // same as .authorities("ROLE_USER", "ROLE_ADMIN")
                    .and()

                .and().jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery("SELECT email as username, password, true FROM user WHERE email=?")
                    .authoritiesByUsernameQuery("SELECT email as username, 'ROLE_USER' FROM user WHERE email=?")
                    .passwordEncoder(new BCryptPasswordEncoder())

                .and().ldapAuthentication()
                    .passwordEncoder(new BCryptPasswordEncoder())
                    .userSearchBase("ou=people")
                    .userSearchFilter("(uid={0})")
                    .groupSearchBase("ou=groups")
                    .groupSearchFilter("(uniqueMember={0})")
                    .contextSource()
                        .root("dc=redparty,dc=com")
                        .ldif("classpath:ldap-server.ldif")

                .and().and()
                    .userDetailsService(new UserServiceConfig(userService))
                    .passwordEncoder(new BCryptPasswordEncoder());


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }
}
