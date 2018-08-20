package com.martin.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.util.Objects;

@Document(collection="comments")
public class Comment implements Storable{

    @Id
    private String id;

    @Field("comment")
    private String comment;

    @DBRef
    private Book book;

    public Comment() {
    }

    public Comment(String comment, Book book) {
        this.comment = comment;
        this.book = book;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    @Override
    public String toString() {
        return "Комментарий (" + id + ") " + comment;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment1 = (Comment) o;
        return Objects.equals(comment, comment1.comment) &&
                Objects.equals(book, comment1.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comment, book);
    }
}
