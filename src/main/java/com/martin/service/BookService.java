package com.martin.service;

import com.martin.domain.Book;
import com.martin.domain.Comment;

import java.util.List;

public interface BookService {

    Book add(String title, String authorId, String genreId) throws Exception;
    Book add(String authorTitle, String authorFirsname, String authorLastname, String genreTitle) throws Exception;
    long getCount();
    List<Book> getAll(int page, int amountByOnePage);
    List<Book> findByTitle(String title) throws Exception;
    Book findById(String id) throws Exception;
    List<Comment> getComments(String id);
    Book update(String id, String title) throws Exception;
    Book update(String id, String bookTitle, String authorId, String genreId) throws Exception;
    void delete(String id) throws Exception;
    void deleteAll();
}
