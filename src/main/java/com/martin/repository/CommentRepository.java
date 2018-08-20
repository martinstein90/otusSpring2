package com.martin.repository;

import com.martin.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {

    Iterable<Comment> findByCommentContaining(String sub);
}
