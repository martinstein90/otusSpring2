package com.martin.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

import static com.martin.domain.Comment.COLLECTION_TITLE;


@Document(collection=COLLECTION_TITLE)
public class Comment implements Storable {

    public static final String COLLECTION_TITLE = "comments";
    public static final String FIELD_COMMENT = "comment";

    @Id
    private ObjectId id;

    @Field(FIELD_COMMENT)
    private String comment;

    public Comment() {
    }

    public Comment(String comment) {
        this.comment = comment;
    }

    @Override
    public ObjectId getId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment1 = (Comment) o;
        return Objects.equals(id, comment1.id) &&
                Objects.equals(comment, comment1.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comment);
    }
}
