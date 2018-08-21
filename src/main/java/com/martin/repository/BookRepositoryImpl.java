package com.martin.repository;

import com.martin.domain.Book;
import com.martin.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


public class BookRepositoryImpl implements BookRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void addComment(String bookId, Comment comment){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(bookId));
        Update update = new Update();
        update.addToSet("comments", comment.getId());
        mongoTemplate.updateMulti(query, update, Book.class);
    }
}
