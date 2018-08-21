package com.martin;

import com.google.common.collect.Lists;
import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Comment;
import com.martin.domain.Genre;
import com.martin.service.AuthorService;
import com.martin.service.BookService;
import com.martin.service.CommentService;
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
        CommentService commentService= context.getBean(CommentService.class);


        Author author1 = authorService.add("Martin", "Stein");
        System.out.println(author1);
        Author author2 = authorService.add("Alex", "Pushkin");
        System.out.println(author1);
        System.out.println(author2);
        Genre genre1 = genreService.add("horror");
        System.out.println(genre1);
        Genre genre2 = genreService.add("comedy");
        System.out.println(genre2);

        Book book1 = bookService.add("book1", author1.getId(), genre1.getId());
        System.out.println(book1);
        Book book2 = bookService.add("book2", author1.getId(), genre1.getId());
        System.out.println(book2);
        Book book3 = bookService.add("book3", author1.getId(), genre1.getId());
        System.out.println(book3);
        Book book4 = bookService.add("book4", author2.getId(), genre1.getId());
        System.out.println(book4);
        Book book5 = bookService.add("book5", author2.getId(), genre2.getId());
        System.out.println(book5);


        List<Book> booksByAuthor = bookService.findByAuthor(author1.getId());
        for (Book book: booksByAuthor) {
            System.out.println(book);
        }

        List<Book> booksByGenre = bookService.findByGenre(genre1.getId());
        for (Book book: booksByGenre) {
            System.out.println(book);
        }

        Comment comment1 = commentService.add("Comment1");
        Comment comment2 = commentService.add("Comment2");
        Comment comment3 = commentService.add("Comment3");
        Comment comment4 = commentService.add("Comment4");
        Comment comment5 = commentService.add("Comment5");

        bookService.addComments(book1.getId(), comment1.getId());
        bookService.addComments(book1.getId(), comment2.getId());
        bookService.addComments(book1.getId(), comment3.getId());
        bookService.addComments(book2.getId(), comment4.getId());
        bookService.addComments(book2.getId(), comment5.getId());

        List<Book> all = bookService.getAll(0, 100);
        for (Book book: all) {
            System.out.println(book);
        }


//        authorService.deleteAll();
//        genreService.deleteAll();
//        bookService.deleteAll();
    }
}
