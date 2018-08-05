package com.martin.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name="genre_id")
    private Genre genre;

    @OneToMany(mappedBy="comment", fetch=FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public Book() {
    }

    public Book(String title, long authorId, Object genre) {
    }

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Книга (" + id + ") " + title + " " + author + " " + genre ;
    }
}
