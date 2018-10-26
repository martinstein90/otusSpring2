package com.martin.security;

import com.martin.domain.User;
import com.martin.security.tokens.PrimaryAuthenticationToken;
import com.martin.security.tokens.SecondaryAuthenticationToken;
import com.martin.service.SmsService;
import com.martin.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationManagerImpl implements AuthenticationManager {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private SmsService smsService;

    public AuthenticationManagerImpl() {
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Old authentication" + authentication);

        System.out.println("smsService = " + smsService);
        System.out.println("userService = " + userDetailsService);
        System.out.println("passwordEncoder = " + passwordEncoder);

        Authentication newAuthentication = authentication;

        if(authentication.getClass().equals(UsernamePasswordAuthenticationToken.class)  && !authentication.isAuthenticated()) {
            log.info("Первичная авторизация");

            User user = (User)userDetailsService.loadUserByUsername((String) authentication.getPrincipal());
            if(user == null)
                throw new UsernameNotFoundException("User " + authentication.getPrincipal() + " not found!");

            if(passwordEncoder.matches((CharSequence) authentication.getCredentials(), user.getPassword())) {
                log.info("Первичная авторизация OK");
                newAuthentication = new PrimaryAuthenticationToken(user, "[PROTECTED]");

                String sms = "123";
                smsService.sendSms(user.getPhoneNumber(), sms);

                try {
                    userDetailsService.setUserSms(user.getUsername(), sms);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
                throw new BadCredentialsException("PrimaryAuthenticationException");
        }
        if(authentication.getClass().equals(SecondaryAuthenticationToken.class) && !authentication.isAuthenticated()) {
            log.info("Вторичная авторизация");

            User user = (User)userDetailsService.loadUserByUsername((String) authentication.getPrincipal());
            if (authentication.getCredentials().equals(user.getSms())) {
                log.info("Вторичная авторизация OK");
                newAuthentication = new SecondaryAuthenticationToken(user, "[PROTECTED]", user.getAuthorities());
            }
            else
                throw new BadCredentialsException("SecondaryAuthenticationToken");
        }

        log.info("New authentication: " + newAuthentication);
        return newAuthentication;
    }

}
