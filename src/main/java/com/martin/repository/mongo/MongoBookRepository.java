package com.martin.repository.mongo;

import com.martin.domain.mongo.MongoBook;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import static com.martin.domain.mongo.MongoBook.FIELD_AUTHOR;

@Repository
public interface MongoBookRepository extends MongoRepository<MongoBook, ObjectId> {

    @Query("{'" + FIELD_AUTHOR + ".$id' : ?0}")
    Iterable<MongoBook> findByAuthor(ObjectId authorId);

    Iterable<MongoBook> findByTitleContaining(String sub);
}
