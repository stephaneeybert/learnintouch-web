package com.thalasoft.learnintouch.web.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

// The default redirection on a failed authentication does not make sense for a REST request
// Instead of returning a: 301 MOVED PERMANENTLY simply return a: 401 UNAUTHORIZED
@Component
public final class RestAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 - " + authenticationException.getMessage());
        writer.println("You failed the REST authentication.");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("LearnInTouch REST");
        super.afterPropertiesSet();
    }
    
}