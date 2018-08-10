package com.martin.repository;

import com.martin.domain.Comment;

import java.util.List;

public interface CommentRepository {

    void insert(Comment comment);

    long getCount();
    List<Comment> getAll(int page, int amountByOnePage);

    Comment findById(long id);

    void update(long id, Comment comment);

    void delete(long id);
}
