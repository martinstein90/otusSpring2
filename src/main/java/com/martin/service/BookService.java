package com.martin.service;

import com.martin.domain.Book;

import java.util.List;

public interface BookService {

    void addBook(String title, int authorId, int genreId) throws Exception;
    void addBook(String authorTitle, String authorFirsname, String authorLastname, String genreTitle) throws Exception;
    long getCount();
    List<Book> getAll(int page, int amountByOnePage);
    List<Book> findByTitle(String title) throws Exception;
    Book findById(long id) throws Exception;
    void updateBook(long id, String title) throws Exception;
    void updateBook(long id, String bookTitle, int authorId, int genreId) throws Exception;
    void deleteBook(long id) throws Exception;
}
