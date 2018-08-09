package com.martin;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Comment;
import com.martin.domain.Genre;
import com.martin.repository.*;
import com.martin.service.AuthorService;
import com.martin.service.BookService;
import com.martin.service.CommentService;
import com.martin.service.GenreService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@EnableAspectJAutoProxy
@SpringBootApplication
public class Main {

    private static AuthorRepository authorRepository;
    private static GenreRepository genreRepository;
    private static CommentRepository commentRepository;
    private static BookRepository bookRepository;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class);

        commentRepository = context.getBean(CommentRepository.class);
        bookRepository = context.getBean(BookRepository.class);
        authorRepository = context.getBean(AuthorRepository.class);
        genreRepository = context.getBean(GenreRepository.class);

        System.out.println("\n\n\n=========== books by Author"); //Todo Ok
        List<Book> booksByAuthor = authorRepository.getBooks(1);
        for (Book book: booksByAuthor) {
            System.out.println(book);
        }

        System.out.println("\n\n\n=========== books by Genre"); //Todo Ok
        List<Book>  booksByGenre= genreRepository.getBooks(1);
        for (Book book: booksByGenre) {
            System.out.println(book);
        }

        System.out.println("\n\n\n=========== comments by Book"); //Todo error
        List<Comment> comments = bookRepository.getComments(1);
        for (Comment com: comments) {
            System.out.println(com);
        }


    }
















}
