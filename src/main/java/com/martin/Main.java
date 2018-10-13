package com.martin;

import com.martin.domain.Author;
import com.martin.domain.User;
import com.martin.repository.AuthorRepository;
import com.martin.repository.UserRepository;
import com.martin.security.GrantedAuthorityImpl;
import com.martin.service.AuthorService;
import com.martin.service.UserDetailsServiceImpl;
import com.martin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;


import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;

import static com.martin.security.Role.ADMIN;
import static com.martin.security.Role.USER;

@SpringBootApplication
public class Main {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorService authorService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }


    @PostConstruct
    public void init() throws Exception {
        System.out.println("=======================init");

        userService.addUser("Martin", "admin", "123-12-12",
                new GrantedAuthorityImpl(USER), new GrantedAuthorityImpl(ADMIN));

        userService.addUser("Eric", "user", "123-12-13",
                new GrantedAuthorityImpl(USER));

        authorService.add("Sergey", "Esenin");
        authorService.add("Alex", "Pushkin");
        authorService.add("Fedor", "Dostoevsky");
    }
}
