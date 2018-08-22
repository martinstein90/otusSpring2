package com.martin.repository;

import com.martin.domain.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, ObjectId> {

    Iterable<Comment> findByCommentContaining(String sub);
}
