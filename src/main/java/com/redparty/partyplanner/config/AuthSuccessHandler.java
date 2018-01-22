package com.redparty.partyplanner.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redparty.partyplanner.PartyPlannerApp;
import com.redparty.partyplanner.common.domain.User;
import com.redparty.partyplanner.common.domain.dto.UserSecurityDTO;
import com.redparty.partyplanner.controller.constant.WebConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(PartyPlannerApp.class);

    private final ObjectMapper mapper;

    @Autowired
    AuthSuccessHandler(MappingJackson2HttpMessageConverter messageConverter) {
        this.mapper = messageConverter.getObjectMapper();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader("Access-Control-Allow-Origin", WebConstant.CORS_URL);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        UserSecurityDTO userDetails = (UserSecurityDTO) authentication.getPrincipal();
        User user = userDetails.getUser();

        log.info(user.getName() + " is connected ");

        PrintWriter writer = response.getWriter();
        mapper.writeValue(writer, user);
        writer.flush();
    }

}