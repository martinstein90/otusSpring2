package com.martin.service;

import com.google.common.collect.Lists;
import com.martin.domain.Author;
import com.martin.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorJpaService implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private SecurityService securityService;

    @Override
    public Author add(String firstname, String lastname) {
        Author author = authorRepository.save(new Author(firstname, lastname));

        //Todo Если не использовать @Transactional (она в интерфесе AuthorService стоит)
        // вылетает java.lang.IllegalArgumentException: Transaction must be running
        //Почему ??
        securityService.addSecurity(SecurityContextHolder.getContext().getAuthentication(),
                                    author.getId(),
                                    Author.class);
        return author;
    }

    @Override
    public List<Author> getAll() {
        return Lists.newArrayList(authorRepository.findAll());
    }
}
