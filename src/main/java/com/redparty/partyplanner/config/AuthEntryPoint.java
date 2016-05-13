package com.redparty.partyplanner.config;

import com.redparty.partyplanner.common.exception.helper.ObjectErrorResource;
import com.redparty.partyplanner.controller.constant.WebConstant;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    //todo it is sample only
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", WebConstant.CORS_URL);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
