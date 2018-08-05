package com.martin.domain;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

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

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "Комментарий (" + id + ") " + comment + " " + book;
    }

    public Book getBook() {
        return book;
    }
}
