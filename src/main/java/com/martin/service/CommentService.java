package com.martin.service;

import com.martin.domain.Comment;

import java.util.List;

public interface CommentService {
    void add(String comment, int bookId) throws Exception;
    long getCount();
    List<Comment> getAll(int page, int amountByOnePage);
    Comment findById(long id) throws Exception;
    Comment update(long id, String comment, int bookId) throws Exception;
    void delete(long id) throws Exception;
}
