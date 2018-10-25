package com.martin.service;

import com.martin.domain.Author;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AuthorService {

    @Transactional
    Author add(String firstname, String lastname);

    @PostFilter("hasPermission(filterObject, 'ADMINISTRATION')")
    List<Author> getAll();
}
