package com.martin.repository;

import com.martin.domain.Author;
import com.martin.domain.Book;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AuthorRepository extends MongoRepository<Author, String> {

    @Query(value = "{_id: ?0}", fields = "{books: 1}")
    Iterable<Book> getBooks(String id);

    @ExistsQuery("db.authors.update( {\"_id\": ObjectId(?0)}, {$addToSet: {\"books\": ?1} } )")
    void addBook(String authorId, String bookId);

    Iterable<Author> findByFirstnameOrLastname(String firstname, String lastname);
}

//db.authors.update( {_id: ObjectId("5b7ac1838a80ef10b0839c4e")}, { $addToSet: { Strr: "aaaa3" } } );

//db.authors.find({"_id": ObjectId("5b7ac1838a80ef10b0839c4e")}, {"Strr":1});