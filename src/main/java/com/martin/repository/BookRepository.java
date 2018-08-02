package com.martin.repository;

import com.martin.domain.Book;

import java.util.List;

public interface BookRepository {

    int insert(Book book);

    int getCount();
    List<Book> getAll(int page, int amountByOnePage);

    Book findById(int id);
    List<Book> find(Book book);

    int update(int id, Book book);

    void delete(int id);
}
