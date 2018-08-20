package com.martin.repository;

import com.martin.domain.Book;
import com.martin.domain.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenreRepository extends MongoRepository<Genre, String> {

    //Iterable<Book> getBooks(long id);

    Iterable<Genre> findByTitle(String title);
}
