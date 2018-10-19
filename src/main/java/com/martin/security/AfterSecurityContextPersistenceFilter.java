package com.martin.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

public class AfterSecurityContextPersistenceFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Filter!!!!!!!!!!!");
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;

        if(httpServletRequest.getMethod().equals("POST") && httpServletRequest.getRequestURI().equals("/login")) {
            String username = httpServletRequest.getParameter("username");
            String password = httpServletRequest.getParameter("password");
            String sms = httpServletRequest.getParameter("sms");
            String test = httpServletRequest.getParameter("test");

            System.out.println("username = " + username);
            System.out.println("password = " + password);
            System.out.println(password == null);
            System.out.println(password.isEmpty());
            System.out.println("sms = " + sms);
            System.out.println(sms == null);
            System.out.println(sms.isEmpty());
            System.out.println("--------------------");
            if(username != null && password != null && !password.isEmpty()){
                System.out.println("Filter 1");
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(username, password));
            }
            else if(username != null && sms != null && !sms.isEmpty()) {
                System.out.println("Filter 2");
                SecurityContextHolder.getContext().setAuthentication(
                        new SmsAuthenticationToken(username, sms));
            }
        }

        chain.doFilter(request, response);
    }
}
