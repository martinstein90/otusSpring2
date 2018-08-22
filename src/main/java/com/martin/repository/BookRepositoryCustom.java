package com.martin.repository;

import com.martin.domain.Comment;
import org.bson.types.ObjectId;

import java.util.List;

public interface BookRepositoryCustom {
    void addComment(ObjectId bookId, Comment comment);
}
