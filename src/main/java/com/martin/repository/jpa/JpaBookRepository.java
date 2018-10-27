package com.martin.repository.jpa;


import com.martin.domain.jpa.JpaAuthor;
import com.martin.domain.jpa.JpaBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface JpaBookRepository extends PagingAndSortingRepository<JpaBook, Long> {
    Iterable<JpaBook> findByTitleContaining(String sub);
}
