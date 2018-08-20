package com.martin.repository;

import com.martin.domain.Book;
import com.martin.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {

    //Iterable<Comment> getComments(long id);

    Iterable<Book> findByTitleContaining(String sub);
}
