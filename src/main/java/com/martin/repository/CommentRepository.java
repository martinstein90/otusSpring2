package com.martin.repository;

import com.martin.domain.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    Iterable<Comment> findByCommentContaining(String sub);
}
