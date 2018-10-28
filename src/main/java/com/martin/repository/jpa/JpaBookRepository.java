package com.martin.repository.jpa;


import com.martin.domain.jpa.JpaBook;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaBookRepository extends PagingAndSortingRepository<JpaBook, Long> {
    Iterable<JpaBook> findByTitleContaining(String sub);
}
