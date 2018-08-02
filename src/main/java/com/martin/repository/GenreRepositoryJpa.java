package com.martin.repository;

import com.martin.domain.Genre;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreRepositoryJpa implements GenreRepository {
    @Override
    public int insert(Genre genre) {
        return 0;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public List<Genre> getAll(int page, int amountByOnePage) {
        return null;
    }

    @Override
    public Genre findById(int id) {
        return null;
    }

    @Override
    public List<Genre> find(Genre genre) {
        return null;
    }

    @Override
    public int update(int id, Genre genre) {
        return 0;
    }

    @Override
    public void delete(int id) {

    }
}
