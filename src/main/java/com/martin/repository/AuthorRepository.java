package com.martin.repository;

import com.martin.domain.Author;

import java.util.List;

public interface AuthorRepository {

    void insert(Author author);

    int getCount();
    List<Author> getAll(int page, int amountByOnePage);

    Author findById(int id);
    List<Author> find(Author author);

    int update(int id, Author author);

    void delete(int id);
}
