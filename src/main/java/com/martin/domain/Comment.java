package com.martin.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "comments")
public class Comment implements Storable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100)
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
