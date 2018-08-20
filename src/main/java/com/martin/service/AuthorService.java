package com.martin.service;

import com.martin.domain.Author;
import com.martin.domain.Book;

import java.util.List;

public interface AuthorService {

    Author add(String firsname, String lastname, List<Book> books) throws Exception;
    void addBook(String id, List<Book> books);

    long getCount();
    List<Author> getAll(int page, int amountByOnePage);
    Author findById(String id) throws Exception;
    List<Author> find(String firstname, String lastname) throws Exception;
    List<Book> getBooks(String id);
    Author update(String id, String firstname, String lastname) throws Exception;
    void delete(String id) throws Exception;
    void delete(String id, boolean withBook) throws Exception;
    void deleteAll();
}
