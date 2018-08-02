package com.martin.repository;

import com.martin.domain.Genre;

import java.util.List;

public interface GenreRepository {

    int insert(Genre genre);

    int getCount();
    List<Genre> getAll(int page, int amountByOnePage);

    Genre findById(int id);
    List<Genre> find(Genre genre);

    int update(int id, Genre genre);

    void delete(int id);
}
