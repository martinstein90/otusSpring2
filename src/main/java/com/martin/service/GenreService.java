package com.martin.service;

import com.martin.domain.Book;
import com.martin.domain.Genre;

import java.util.List;

public interface GenreService {

    Genre add(String title) throws Exception;
    long getCount();
    List<Genre> getAll(int page, int amountByOnePage);
    Genre findById(String id) throws Exception;
    List<Genre> find(String title) throws Exception;
    Genre update(String id, String title) throws Exception;
    void delete(String id) throws Exception;
    void deleteAll();
}
