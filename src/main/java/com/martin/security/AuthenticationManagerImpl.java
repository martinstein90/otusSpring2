package com.martin.security;

import com.martin.domain.User;
import com.martin.security.tokens.PrimaryAuthenticationToken;
import com.martin.security.tokens.SecondaryAuthenticationToken;
import com.martin.service.SmsService;
import com.martin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AuthenticationManagerImpl implements AuthenticationManager {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private SmsService smsService;

    public AuthenticationManagerImpl() {
        System.out.println("AuthenticationManagerImpl");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Authenticate");
        System.out.println("Old " + authentication);

        System.out.println("smsService = " + smsService);
        System.out.println("userService = " + userService);
        System.out.println("passwordEncoder = " + passwordEncoder);

        Authentication newAuthentication = authentication;

        if(authentication.getClass().equals(UsernamePasswordAuthenticationToken.class)  && !authentication.isAuthenticated()) {
            System.out.println("Первичная авторизация");
            System.out.println(userService);

            User user = userService.getUser((String) authentication.getPrincipal());

            if(user == null)
                throw new UsernameNotFoundException("User " + authentication.getPrincipal() + "not found!");

            if(passwordEncoder.matches((CharSequence) authentication.getCredentials(), user.getPassword())) {
                System.out.println("Первичная авторизация OK");
                newAuthentication = new PrimaryAuthenticationToken(user, "[PROTECTED]");

                String sms = "123";
                smsService.sendSms(user.getPhoneNumber(), sms);

                try {
                    userService.setUserSms(user.getUsername(), sms);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
                throw new BadCredentialsException("PrimaryAuthenticationException");
        }
        if(authentication.getClass().equals(SecondaryAuthenticationToken.class) && !authentication.isAuthenticated()) {
            System.out.println("Вторичная авторизация");

            User user = userService.getUser((String) authentication.getPrincipal());

            if (authentication.getCredentials().equals(user.getSms())) {
                System.out.println("Вторичная авторизация OK");
                newAuthentication = new SecondaryAuthenticationToken(user, "[PROTECTED]", user.getAuthorities());
            }
            else
                throw new BadCredentialsException("SecondaryAuthenticationToken");
        }
        System.out.println("New: " + newAuthentication);
        return newAuthentication;
    }

}
