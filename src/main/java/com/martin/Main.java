package com.martin;

import com.martin.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.PostConstruct;

@EnableAspectJAutoProxy
@SpringBootApplication
public class Main {

    @Autowired
    private AuthorService authorService;

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class);
    }

    @PostConstruct
    public void init() throws Exception {
        authorService.deleteAll();
        authorService.add("Martin", "Stein");
        authorService.add("Alex", "Pushkin");
        authorService.add("Fedor", "Dostoevsky");
        authorService.add("Anton", "Chechov");
        authorService.add("Sergey", "Esenin");
        authorService.add("Nilolay", "Levashov");
    }
}
