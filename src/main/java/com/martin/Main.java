package com.martin;

import com.martin.security.GrantedAuthorityImpl;
import com.martin.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import javax.annotation.PostConstruct;

import static com.martin.security.Role.ADMIN;
import static com.martin.security.Role.USER;

@SpringBootApplication
public class Main {


    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    @PostConstruct
    public void init() throws Exception {

        userDetailsService.addUser("Martin", "admin", "123-12-12",
                new GrantedAuthorityImpl(USER), new GrantedAuthorityImpl(ADMIN));

        userDetailsService.addUser("Eric", "user", "123-12-13",
                new GrantedAuthorityImpl(USER));
    }
}
