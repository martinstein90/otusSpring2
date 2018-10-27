package com.martin.service.jpa;


import com.martin.domain.jpa.JpaBook;

import java.util.List;

public interface JpaBookService {

    JpaBook add(String title, long authorId) throws Exception;
    JpaBook add(String authorTitle, String authorFirsname, String authorLastname) throws Exception;
    long getCount();
    List<JpaBook> getAll(int page, int amountByOnePage);
    List<JpaBook> findByTitle(String title) throws Exception;
    JpaBook findById(long id) throws Exception;
    JpaBook update(long id, String title) throws Exception;
    JpaBook update(long id, String bookTitle, long authorId) throws Exception;
    void delete(long id) throws Exception;
    void deleteAll();
}