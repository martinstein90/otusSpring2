package com.martin.repository;

import com.martin.domain.Genre;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenreRepository extends MongoRepository<Genre, ObjectId> {

    Iterable<Genre> findByTitle(String title);
}
