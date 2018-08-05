package com.martin;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.service.AuthorService;
import com.martin.service.GenreService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;
import java.util.Set;


@EnableAspectJAutoProxy
@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class);
        AuthorService serviceAuthor = context.getBean(AuthorService.class);
        GenreService serviceGenre = context.getBean(GenreService.class);


        List<Book> books = serviceGenre.getBooks(1);



        for (Book b: books) {
            System.out.println(b);
        }

        //Console.main(args);
    }
}
