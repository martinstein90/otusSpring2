package com.martin.domain;

import com.martin.domain.jpa.JpaAuthor;
import com.martin.domain.mongo.MongoAuthor;

public class JpaToMongoTransfer {

    public static MongoAuthor JpaToMongoAuthor(JpaAuthor jpaAuthor) {
        return new MongoAuthor( jpaAuthor.getFirstname(), jpaAuthor.getLastname());
    }
}
