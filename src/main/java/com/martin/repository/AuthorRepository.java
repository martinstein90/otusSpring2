package com.martin.repository;

import com.martin.domain.Author;
import com.martin.domain.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {

    @Query("select b from Book b left join b.author a where a.id = ?1")
    Iterable<Book> getBooks(long id);

    Iterable<Author> findByFirstnameOrLastname(String firstname, String lastname);
}
