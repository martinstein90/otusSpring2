package com.martin.service;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;

import java.util.List;

public interface LibraryService {

    void addAuthor(String firsname, String lastname) throws Exception;
    void addGenre(String title) throws Exception;
    void addBook(String title, int authorId, int genreId) throws Exception;
    void addBook(String title, String firsname, String lastname, String titleGenre) throws Exception;

    int getAuthorsCount();
    int getGenresCount();
    int getBooksCount();

    List<Author> getAllAuthors(int page, int amountByOnePage);
    List<Genre> getAllGenres(int page, int amountByOnePage);
    List<Book> getAllBooks(int page, int amountByOnePage);

    List<Author> findAuthor (String authorFirstname, String authotLastname) throws Exception;
    List<Genre> findGenre  (String genreTitle) throws Exception;
    List<Book> findBookByTitle    (String bookTitle) throws Exception;
    List<Book> findBooksByAuthor  (int authorId) throws Exception;
    List<Book> findBooksByGenre   (int genreId) throws Exception;

    Author findAuthorById (int authorId) throws Exception;
    Genre findGenreById  (int genreId) throws Exception;
    Book findBookById   (int bookId) throws Exception;

    void updateAuthor   (int authorId, String authorFirstname, String authotLastname) throws Exception;
    void updateGenre    (int genreId, String genreTitle) throws Exception;
    void updateBook     (int bookId, String bookTitle) throws Exception;
    void updateBook     (int bookId, String bookTitle, int authorId, int genreId) throws Exception;

    void deleteBook     (int deletedBookId) throws Exception;
    void deleteAuthor   (int deleteAuthor) throws Exception;
    void deleteGenre    (int deletedGenreId) throws Exception;
}
