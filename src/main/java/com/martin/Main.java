package com.martin;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Comment;
import com.martin.domain.Genre;
import com.martin.repository.AuthorRepository;
import com.martin.service.AuthorService;
import com.martin.service.BookService;
import com.martin.service.CommentService;
import com.martin.service.GenreService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.PostConstruct;
import java.util.List;

@EnableAspectJAutoProxy
@SpringBootApplication
public class Main {

    @Autowired
    private AuthorService service;

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class);
    }


//    @PostConstruct
//    public void init() throws Exception {
//        Author author1 = service.add("Martin1", "Stein");
//        System.out.println(author1);
//        Author author2 = service.add("Alex", "Pushkin");
//        System.out.println(author2);
//        Author author3 = service.add("Fedor", "Dostoevsky");
//        System.out.println(author3);
//    }
}
