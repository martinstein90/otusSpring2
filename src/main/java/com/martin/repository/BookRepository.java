package com.martin.repository;

import com.martin.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

    @Query("{'authors.$id' : ?0}")  //Todo захардкошены authors, genres. Это очень плохо. Как исправить?
    Iterable<Book> findByAuthor(String authorId);

    @Query("{'genres.$id' : ?0}")
    Iterable<Book> findByGenre(String genreId);

    Iterable<Book> findByTitleContaining(String sub);
}
