package com.redparty.partyplanner.config;

import com.redparty.partyplanner.config.csrf.CsrfTokenResponseCookieBindingFilter;
import com.redparty.partyplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.encoding.BaseDigestPasswordEncoder;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserServiceConfig userServiceConfig;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AuthEntryPoint authEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userServiceConfig)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS, "/*/**").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/").access("permitAll") //SpEL
                    .anyRequest().authenticated()

                .and()
                    .formLogin() // form based auth
                .and()
                    .exceptionHandling().authenticationEntryPoint(authEntryPoint) //todo
                .and()
                    .httpBasic() //http based auth
                .and()
                    .rememberMe()
                        .tokenValiditySeconds(1000000)
                        .key("RedParty")
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                    .deleteCookies("remember-me")

                .and()
                    .csrf().requireCsrfProtectionMatcher(
                            new AndRequestMatcher(

                                    new NegatedRequestMatcher(new AntPathRequestMatcher("/", HttpMethod.OPTIONS.toString())),
                                    new NegatedRequestMatcher(new AntPathRequestMatcher("/login*/**", HttpMethod.OPTIONS.toString())),
                                    new NegatedRequestMatcher(new AntPathRequestMatcher("/logout*/**", HttpMethod.OPTIONS.toString())),

                                    new NegatedRequestMatcher(new AntPathRequestMatcher("/**", HttpMethod.GET.toString())),
                                    new NegatedRequestMatcher(new AntPathRequestMatcher("/**", HttpMethod.HEAD.toString())),
                                    new NegatedRequestMatcher(new AntPathRequestMatcher("/**", HttpMethod.OPTIONS.toString())),
                                    new NegatedRequestMatcher(new AntPathRequestMatcher("/**", HttpMethod.TRACE.toString()))
                            )
                    )
                .and()
                    .addFilterAfter(new CsrfTokenResponseCookieBindingFilter(), CsrfFilter.class); // CSRF tokens handling;

    }
}
