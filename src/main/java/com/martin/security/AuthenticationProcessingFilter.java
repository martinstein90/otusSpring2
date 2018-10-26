package com.martin.security;

import com.martin.security.tokens.PrimaryAuthenticationToken;
import com.martin.security.tokens.SecondaryAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class AuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    public AuthenticationProcessingFilter(@Autowired  AuthenticationManagerImpl authenticationManager) {
        super(new AntPathRequestMatcher("/login", "POST"));
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(new AuthenticationSuccessHandlerImpl());
        setAuthenticationFailureHandler(new AuthenticationFailureHandlerImpl());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        log.info("attemptAuthentication");

        Authentication authentication = null;

        Set<String> parameters = new HashSet<>(Collections.list(request.getParameterNames()));

        if(parameters.contains("username") && parameters.contains("password")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            authentication = new UsernamePasswordAuthenticationToken(username, password);
        }
        else if(parameters.contains("username") && parameters.contains("sms")) {
            String username = request.getParameter("username");
            String sms = request.getParameter("sms");
            authentication = new SecondaryAuthenticationToken(username, sms);
        }
        return getAuthenticationManager().authenticate(authentication);
    }


    private class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

            if(authentication instanceof PrimaryAuthenticationToken) {
                log.info("PrimaryAuthenticationToken");
                request.setAttribute("username", authentication.getPrincipal().toString());
                request.getRequestDispatcher("/success").forward(request, response);
            }
            if(authentication instanceof SecondaryAuthenticationToken) {
                log.info("SecondaryAuthenticationToken");

                HttpSession session = request.getSession();
                SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
                new DefaultRedirectStrategy().sendRedirect(request, response, savedRequest.getRedirectUrl());
            }
        }
    }

    private class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            request.getRequestDispatcher("/error").forward(request, response);
        }
    }
}