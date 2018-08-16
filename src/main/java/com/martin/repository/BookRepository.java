package com.martin.repository;

import com.martin.domain.Book;
import com.martin.domain.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    @Query("select c from Comment c left join c.book b where b.id = ?1")
    Iterable<Comment> getComments(long id);
    Iterable<Book> findByTitleContaining(String sub);
}
