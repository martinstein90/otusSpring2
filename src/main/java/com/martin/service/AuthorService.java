package com.martin.service;

import com.martin.domain.Author;
import com.martin.domain.Book;

import java.util.List;

public interface AuthorService {

    Author add(String firsname, String lastname) throws Exception;
    long getCount();
    List<Author> getAll(int page, int amountByOnePage);
    Author findById(String id) throws Exception;
    List<Author> find(String firstname, String lastname) throws Exception;
    Author update(String id, String firstname, String lastname) throws Exception;
    void delete(String id) throws Exception;
    void deleteAll();
}
