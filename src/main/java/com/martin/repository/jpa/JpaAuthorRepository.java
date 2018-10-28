package com.martin.repository.jpa;

import com.martin.domain.jpa.JpaAuthor;
import com.martin.domain.jpa.JpaBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;

@Repository
public interface JpaAuthorRepository extends PagingAndSortingRepository<JpaAuthor, Long> {

    @Query("select b from JpaBook b left join b.author a where a.id = ?1")
    Iterable<JpaBook> getBooks(long id);

    Iterable<JpaAuthor> findByFirstnameOrLastname(String firstname, String lastname);
}
