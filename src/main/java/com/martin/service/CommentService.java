package com.martin.service;

import com.martin.domain.Book;
import com.martin.domain.Comment;

import java.util.List;

public interface CommentService {
    Comment add(String comment, String bookId) throws Exception;
    long getCount();
    List<Comment> getAll(int page, int amountByOnePage);
    Comment findById(String id) throws Exception;
    List<Comment> find(String subTitle) throws Exception;
    Comment update(String id, String comment,  String bookId) throws Exception;
    void delete(String id) throws Exception;
    void deleteAll();
}
