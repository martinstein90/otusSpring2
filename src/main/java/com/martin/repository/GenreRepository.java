package com.martin.repository;

import com.martin.domain.Book;
import com.martin.domain.Genre;

import java.util.List;

public interface GenreRepository {

    void insert(Genre genre);

    long getCount();
    List<Genre> getAll(int page, int amountByOnePage);

    Genre findById(long id);
    List<Genre> find(Genre genre);

    List<Book> getBooks(long id);

    int update(long id, Genre genre);

    void delete(long id);
}
