package com.martin.repository;

import com.martin.domain.Comment;

public interface BookRepositoryCustom {
    void addComment(String bookId, Comment comment);
}
