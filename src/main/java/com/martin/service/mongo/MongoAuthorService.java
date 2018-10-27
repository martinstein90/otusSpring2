package com.martin.service.mongo;

import com.martin.domain.mongo.MongoAuthor;

import org.bson.types.ObjectId;

import java.util.List;

public interface MongoAuthorService {
    MongoAuthor add(String firsname, String lastname) throws Exception;
    long getCount();
    List<MongoAuthor> getAll(int page, int amountByOnePage);
    MongoAuthor findById(ObjectId id) throws Exception;
    List<MongoAuthor> find(String firstname, String lastname) throws Exception;
    MongoAuthor update(ObjectId id, String firstname, String lastname) throws Exception;
    void delete(ObjectId id) throws Exception;
    void deleteAll();
}