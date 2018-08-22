package com.martin.repository;

import com.martin.domain.Book;
import com.martin.domain.Comment;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static com.martin.domain.Book.FIELD_COMMENTS;

public class BookRepositoryImpl implements BookRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    BookRepository bookRepository;

    @Override
    public void addComment(ObjectId bookId, Comment comment){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(bookId));
        Update update = new Update();
        update.addToSet(FIELD_COMMENTS, comment.getId());
        mongoTemplate.updateMulti(query, update, Book.class);
    }
}
