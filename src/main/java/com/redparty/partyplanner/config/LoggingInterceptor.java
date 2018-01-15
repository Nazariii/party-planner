package com.redparty.partyplanner.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Map;

@Component
class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
    private static final String REQUEST_PREFIX = "=== Request: ===";
    private static final String RESPONSE_PREFIX = "Response: ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logRequest(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        logResponse(response);
    }

    private void logRequest(final HttpServletRequest request) {
        StringBuilder msg = new StringBuilder();
        msg.append("\n").append("\n").append(REQUEST_PREFIX).append("\n");

        HttpSession session = request.getSession(false);
        if (session != null) {
            msg.append("Session id = ").append(session.getId()).append("; ").append("\n");
        }
        if (request.getMethod() != null) {
            msg.append("METHOD = ").append(request.getMethod()).append("; ").append("\n");
        }
        if (request.getContentType() != null) {
            msg.append("CONTENT TYPE = ").append(request.getContentType()).append("; ").append("\n");
        }
        msg.append("URI = ").append(request.getRequestURI()).append("\n");
        if (request.getQueryString() != null) {
            msg.append('?').append(request.getQueryString()).append("\n");
        }

        Enumeration<String> headers = request.getHeaderNames();
        if (headers != null) {
            msg.append("Headers: ").append("\n");
            while (headers.hasMoreElements()) {
                String header = headers.nextElement();
                msg.append("         ").append(header).append(" : ").append(request.getHeader(header)).append("\n");
            }
        }

        Map<String, String[]> parameters = request.getParameterMap();
        if (parameters != null && parameters.size() > 0) {
            msg.append("Parameters: ").append("\n");
            parameters.forEach((s, strings) -> msg.append("         ").append(s).append(" = ").append(strings).append("\n"));
        }

        logger.debug(msg.toString());
    }

    private void logResponse(final HttpServletResponse request) {
        StringBuilder msg = new StringBuilder();
        msg.append("\n").append("\n").append(RESPONSE_PREFIX).append("\n");

        msg.append("Status = ").append(request.getStatus()).append("; ").append("\n");

        if (request.getContentType() != null) {
            msg.append("CONTENT TYPE = ").append(request.getContentType()).append("; ").append("\n");
        }

        Iterable<String> headers = request.getHeaderNames();
        if (headers != null) {
            msg.append("Headers: ").append("\n");
            for (String header : headers) {
                msg.append("         ").append(header).append(" : ").append(request.getHeader(header)).append("\n");
            }
        }

        logger.debug(msg.toString());
    }

/*    private boolean isBinaryContent(final HttpServletRequest request) {
        return request.getContentType() != null
                && (request.getContentType().startsWith("image")
                || request.getContentType().startsWith("video")
                || request.getContentType().startsWith("audio"));
    }

    private boolean isMultipart(final HttpServletRequest request) {
        return request.getContentType() != null && request.getContentType().startsWith("multipart/form-data");
    }*/

}