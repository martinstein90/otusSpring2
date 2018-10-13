package com.martin.service;

import com.martin.domain.Author;

import java.util.List;

public interface AuthorService {
    Author add(String firsname, String lastname) throws Exception;
    List<Author> getAll();


}
