package com.martin.repository.mongo;

import com.martin.domain.mongo.MongoAuthor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoAuthorRepository extends MongoRepository<MongoAuthor, ObjectId> {
    Iterable<MongoAuthor> findByFirstnameOrLastname(String firstname, String lastname);
}