package com.martin.repository;

import com.martin.domain.Book;
import com.martin.domain.Comment;

import java.util.List;

public interface BookRepository {

    void insert(Book book);

    long getCount();
    List<Book> getAll(int page, int amountByOnePage);

    Book findById(long id);
    List<Book> find(Book book);

    List<Comment> getComments(long id);

    void update(long id, Book book);

    void delete(long id);
}
