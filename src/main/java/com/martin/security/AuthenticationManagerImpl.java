package com.martin.security;

import com.martin.domain.User;
import com.martin.service.SmsService;
import com.martin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@Component
public class AuthenticationManagerImpl implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private SmsService smsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Authenticate");
        System.out.println("Old " + authentication);

        Authentication newAuthentication = null;

        if(authentication instanceof UsernamePasswordAuthenticationToken &&
        authentication.isAuthenticated()== false) {
            System.out.println("Первичная авторизация");
            //Сюда мы зашли, когда пользователь отправил свой логин и пароль
            User user = userService.getUser((String) authentication.getPrincipal());

            if(passwordEncoder.matches((CharSequence) authentication.getCredentials(), user.getPassword())) {
                String sms = "12345";
                newAuthentication = new UsernamePasswordWithSMSAuthenticationToken(authentication.getName(), authentication.getCredentials(),
                        user.getAuthorities(), sms);
                newAuthentication.setAuthenticated(false);

                smsService.sendSms(user.getPhoneNumber(), sms);

                try {
                    userService.setUserSms(user.getUsername(), sms);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            else newAuthentication = new AnonymousAuthenticationToken("key", "password", Collections.emptyList());
        }
        if(authentication instanceof UsernamePasswordWithSMSAuthenticationToken &&
                authentication.isAuthenticated()== false) {
            System.out.println("Вторичная авторизация");
            //Сюда мы зашли, когда пользователь уже аутентифицирован по паролю, нужно как то прикрутить проверку смс
            User user = userService.getUser((String) authentication.getPrincipal());

            if ( ((UsernamePasswordWithSMSAuthenticationToken) authentication).getSms().equals(user.getSms()))
                newAuthentication =  new UsernamePasswordWithSMSAuthenticationToken(authentication.getName(), authentication.getCredentials(),
                        user.getAuthorities());
            newAuthentication.setAuthenticated(true);
        }

        System.out.println("New: " + newAuthentication);
        return newAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true; //authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}