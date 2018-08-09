package com.martin.domain;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment implements Storable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String comment;

    @ManyToOne
    @JoinColumn(name="book_id")
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

    public Book getBook() {
        return book;
    }

    @Override
    public String toString() {
        return "Комментарий (" + id + ") " + comment;
    }

    @Override
    public long getId() {
        return 0;
    }
}
