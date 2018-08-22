package com.martin.service;

import com.martin.domain.Comment;
import org.bson.types.ObjectId;

import java.util.List;

public interface CommentService {
    Comment add(String comment) throws Exception;
    long getCount();
    List<Comment> getAll(int page, int amountByOnePage);
    Comment findById(ObjectId id) throws Exception;
    List<Comment> find(String subTitle) throws Exception;
    Comment update(ObjectId id, String comment) throws Exception;
    void delete(ObjectId id) throws Exception;
    void deleteAll();
}
