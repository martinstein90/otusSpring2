package com.martin.repository;

import com.martin.domain.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenreRepository extends MongoRepository<Genre, String> {

    Iterable<Genre> findByTitle(String title);
}
