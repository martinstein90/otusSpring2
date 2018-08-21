package com.martin.repository;

import com.martin.domain.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepository extends MongoRepository<Author, String> {

    Iterable<Author> findByFirstnameOrLastname(String firstname, String lastname);
}

