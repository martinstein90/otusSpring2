package com.martin.service;


import com.martin.repository.AuthorRepository;

import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Service;


@Service
@Scope(value = "singleton")
public class AuthorJpaService implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorJpaService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


}
