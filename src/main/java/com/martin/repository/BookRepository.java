package com.martin.repository;

import com.martin.domain.Book;

import java.util.List;

public interface BookRepository {

    void insert(Book book);

    long getCount();
    List<Book> getAll(int page, int amountByOnePage);

    Book findById(long id);
    List<Book> find(Book book);

    int update(long id, Book book);

    void delete(long id);
}
