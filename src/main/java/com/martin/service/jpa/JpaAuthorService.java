package com.martin.service.jpa;


import com.martin.domain.jpa.JpaAuthor;
import com.martin.domain.jpa.JpaBook;

import java.util.List;

public interface JpaAuthorService {

    JpaAuthor add(String firsname, String lastname) throws Exception;
    long getCount();
    List<JpaAuthor> getAll(int page, int amountByOnePage);
    JpaAuthor findById(long id) throws Exception;
    List<JpaAuthor> find(String firstname, String lastname) throws Exception;
    List<JpaBook> getBooks(long id);
    JpaAuthor update(long id, String firstname, String lastname) throws Exception;
    void delete(long id) throws Exception;
    void delete(long id, boolean withBook) throws Exception;
    void deleteAll();
}