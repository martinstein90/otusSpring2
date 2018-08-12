package com.martin.repository;

import com.martin.domain.Book;
import com.martin.domain.Genre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GenreRepository extends PagingAndSortingRepository<Genre, Long> {

    @Query("select b from Book b left join b.genre g where g.id = ?1")
    Iterable<Book> getBooks(long id);

    Iterable<Genre> findByTitle(String title);
}
