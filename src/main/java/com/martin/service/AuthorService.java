package com.martin.service;

import com.martin.domain.Author;
import com.martin.domain.Book;

import java.util.List;

public interface AuthorService {

    Author add(String firsname, String lastname) throws Exception;
    long getCount();
    List<Author> getAll(int page, int amountByOnePage);
    Author findById(long id) throws Exception;
    List<Author> find(String firstname, String lastname) throws Exception;
    List<Book> getBooks(long id);
    Author update(long id, String firstname, String lastname) throws Exception;
    void delete(long id) throws Exception;
}
