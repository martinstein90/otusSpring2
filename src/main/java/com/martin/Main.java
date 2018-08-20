package com.martin;

import com.google.common.collect.Lists;
import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import com.martin.service.AuthorService;
import com.martin.service.BookService;
import com.martin.service.GenreService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.ArrayList;
import java.util.List;


@EnableAspectJAutoProxy
@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class);

        AuthorService authorService = context.getBean(AuthorService.class);
        GenreService genreService = context.getBean(GenreService.class);
        BookService bookService = context.getBean(BookService.class);



        Author author = authorService.add("Martin", "Stein", null);
        System.out.println(author);
        Genre genre = genreService.add("horror");
        System.out.println(genre);

        Book book1 = bookService.add("book1", author.getId(), genre.getId());
        Book book2 = bookService.add("book2", author.getId(), genre.getId());

        System.out.println(book1);
        System.out.println(book2);

        authorService.addBook(author.getId(), Lists.newArrayList(book1, book2));


        List<Book> ids = authorService.getBooks(author.getId());
        for (Book id: ids) {
            System.out.println(id);
        }


//        authorService.deleteAll();
//        genreService.deleteAll();
//        bookService.deleteAll();
    }
}
