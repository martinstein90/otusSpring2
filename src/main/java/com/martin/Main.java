package com.martin;

import com.martin.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.Disposable;

import javax.annotation.PostConstruct;

@EnableAspectJAutoProxy
@SpringBootApplication
public class Main {

    @Autowired
    private AuthorService authorService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    @PostConstruct
    public void init() throws Exception {
        System.out.println("=======================init");

//        authorService.deleteAll().subscribe(System.out::println);
//        authorService.add("Martin", "Stein").subscribe();
        //Todo Почему программа висит на .block(); В то время когда .block() прекрасно работает в сервисах.
//        authorService.add("Alex", "Pushkin").block();
//        authorService.add("Fedor", "Dostoevsky").block();
//        authorService.add("Anton", "Chechov").block();
//        authorService.add("Sergey", "Esenin").block();
//        authorService.add("Nilolay", "Levashov").block();
    }
}
