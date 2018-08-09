package com.martin.service;

import com.martin.domain.Book;
import com.martin.domain.Comment;

import java.util.List;

public interface BookService {

    Book add(String title, int authorId, int genreId) throws Exception;
    Book add(String authorTitle, String authorFirsname, String authorLastname, String genreTitle) throws Exception;
    long getCount();
    List<Book> getAll(int page, int amountByOnePage);
    List<Book> findByTitle(String title) throws Exception;
    Book findById(long id) throws Exception;
    List<Comment> getComments(long id);
    Book update(long id, String title) throws Exception;
    Book update(long id, String bookTitle, int authorId, int genreId) throws Exception;
    void delete(long id) throws Exception;
}
