package com.martin;

import com.martin.dao.LibraryDao;
import com.martin.dao.LibraryJDBCdao;
import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import com.martin.service.LibraryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

@EnableAspectJAutoProxy
@SpringBootApplication
public class Main {

    public static void main(String[] args)  {
        ApplicationContext context = SpringApplication.run(Main.class);
        LibraryService service = context.getBean(LibraryService.class);


//        try {
//            service.addGenre(null);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

        try {
            service.deleteBook(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    public static void getCountAuthor(LibraryDao dao) {
        System.out.println("count authors: " + dao.getCount(Author.class));
    }

    public static void getCountGenre(LibraryDao dao) {
        System.out.println("count genres: " + dao.getCount(Genre.class));
    }

    public static void getCountBook(LibraryDao dao) {
        System.out.println("count book: " + dao.getCount(Book.class));
    }


    public static void getAllAuthors(LibraryDao dao) {
        List<Author> authors = dao.getAll(Author.class, 1, 5);
        System.out.println("Authors 1");
        for (Author a:authors ) {
            System.out.println(a);
        }
        authors = dao.getAll(Author.class, 2, 5);
        System.out.println("Authors 2");
        for (Author a:authors ) {
            System.out.println(a);
        }
        authors = dao.getAll(Author.class, 3, 5);
        System.out.println("Authors 3");
        for (Author a:authors ) {
            System.out.println(a);
        }
    }

    public static void getAllGenres(LibraryDao dao) {
        List<Genre> genres = dao.getAll(Genre.class, 1, 5);
        System.out.println("Genre 1");
        for (Genre a : genres) {
            System.out.println(a);
        }
        genres = dao.getAll(Genre.class, 2, 5);
        System.out.println("Genre 2");
        for (Genre a : genres) {
            System.out.println(a);
        }
    }

    public static void getAllBooks(LibraryDao dao) {
        List<Book> books = dao.getAll(Book.class, 1, 5);
        System.out.println("Book 1");
        for (Book a : books) {
            System.out.println(a);
        }
        books = dao.getAll(Book.class, 2, 5);
        System.out.println("Book 2");
        for (Book a : books) {
            System.out.println(a);
        }
    }


    public static void findAuthor(LibraryDao dao) {
        List<Author> authors = dao.find(new Author("Федор", null));
        for (Author a:authors ) {
            System.out.println(a);
        }
    }

    public static void findGenre(LibraryDao dao) {
        List<Genre> genres = dao.find(new Genre("Ужасы"));
        for (Genre a: genres ) {
            System.out.println(a);
        }
    }

    public static void findBook(LibraryDao dao) {
        Author author = dao.findById(Author.class, 4);
        List<Book> books = dao.find(new Book("Опасные связи", author, null));
        for (Book a: books ) {
            System.out.println(a);
        }
    }

    public static void finAuthorById(LibraryDao dao) {
        System.out.println("Author by id = 7: " + dao.findById(Author.class, 7));
    }

    public static void finGenreById(LibraryDao dao) {
        System.out.println("Genre by id = 10: " + dao.findById(Genre.class, 10));
    }

    public static void finBookById(LibraryDao dao) {
        System.out.println("Genre by id = 10: " + dao.findById(Book.class, 10));
    }

    public static void updateAuthor(LibraryDao dao) {
        dao.update(23, new Author("Кон", "Пет!!!!!"));
    }

    public static void updateGenre(LibraryDao dao) {
        dao.update(7, new Genre("Ужасы!!!!"));
    }

    public static void updateBook(LibraryDao dao) {
        Author author = dao.findById(Author.class, 1);
        Genre genre = dao.findById(Genre.class, 1);
        Book book = new Book("rrrrrrrrrrr", author, genre);
        dao.update(11, book);
    }


    public static void deleteAuthor(LibraryDao dao) {
        dao.delete(Author.class, 23);
    }

    public static void deleteGenre(LibraryDao dao) {
        dao.delete(Genre.class, 7);
    }

    public static void deleteBook(LibraryDao dao) {
        dao.delete(Book.class, 15);
    }
}
