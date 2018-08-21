package com.martin.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection="comments")
public class Comment implements Storable {

    @Id
    private String id;

    @Field("comment")
    private String comment;

    public Comment() {
    }

    public Comment(String comment) {
        this.comment = comment;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "Комментарий (" + id + ") " + comment;
    }

}
