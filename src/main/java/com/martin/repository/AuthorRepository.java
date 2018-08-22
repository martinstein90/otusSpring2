package com.martin.repository;

import com.martin.domain.Author;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepository extends MongoRepository<Author, ObjectId> {

    Iterable<Author> findByFirstnameOrLastname(String firstname, String lastname);
}

