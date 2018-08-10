package com.martin.repository;

import com.martin.domain.Author;
import com.martin.domain.Book;

import java.util.List;

public interface AuthorRepository {

    void insert(Author author);

    long getCount();
    List<Author> getAll(int page, int amountByOnePage);

    Author findById(long id);
    List<Author> find(Author author);

    List<Book> getBooks(long id);

    void update(long id, Author author);

    void delete(long id);
}
