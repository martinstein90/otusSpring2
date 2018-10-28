package com.martin;

import com.martin.domain.jpa.JpaAuthor;
import com.martin.repository.jpa.JpaAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaRepositories
@EnableMongoRepositories
public class Main {

    @Autowired
    private JpaAuthorRepository jpaAuthorRepository;

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }


//    @PostConstruct
//    public void init() throws Exception {
//        for(int i=0; i<150; i++)
//            jpaAuthorRepository.save(new JpaAuthor("Alex" + i, "Pushkin" + i));
//    }
}
