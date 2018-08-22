package com.martin.repository;

import com.martin.domain.Book;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import static com.martin.domain.Book.FIELD_AUTHOR;
import static com.martin.domain.Book.FIELD_GENRE;

public interface BookRepository extends MongoRepository<Book, ObjectId>, BookRepositoryCustom {

    @Query("{'" + FIELD_AUTHOR + ".$id' : ?0}")
    Iterable<Book> findByAuthor(ObjectId authorId);

    @Query("{'" + FIELD_GENRE + ".$id' : ?0}")
    Iterable<Book> findByGenre(ObjectId genreId);

    Iterable<Book> findByTitleContaining(String sub);
}
