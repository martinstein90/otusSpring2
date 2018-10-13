package com.martin.service;


import com.google.common.collect.Lists;
import com.martin.domain.Author;
import com.martin.repository.AuthorRepository;

import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class AuthorJpaService implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorJpaService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author add(String firsname, String lastname)  {
        Author author = new Author(firsname, lastname);
        authorRepository.save(author);
        return author;
    }

    @Override
    public List<Author> getAll() {
        return new ArrayList<>(Lists.newArrayList(authorRepository.findAll()));
    }
}
