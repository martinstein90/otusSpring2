package com.martin.service;

import com.martin.domain.Book;
import com.martin.domain.Comment;

import java.util.List;

public interface CommentService {
    Comment add(String comment, int bookId) throws Exception;
    long getCount();
    List<Comment> getAll(int page, int amountByOnePage);
    Comment findById(long id) throws Exception;
    List<Comment> find(String subTitle) throws Exception;
    Comment update(long id, String comment,  int bookId) throws Exception;
    void delete(long id) throws Exception;
    void deleteAll();
}
