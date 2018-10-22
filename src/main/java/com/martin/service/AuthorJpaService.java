package com.martin.service;

import com.martin.domain.Author;
import com.martin.repository.AuthorRepository;
import com.martin.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthorJpaService implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private SecurityService securityService;

    @Override
    public Author add(String firstname, String lastname) {

        Author author = authorRepository.save(new Author(firstname, lastname));

        String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        securityService.addSecurity(principal, author.getId(), Author.class);

        return author;
    }
}
