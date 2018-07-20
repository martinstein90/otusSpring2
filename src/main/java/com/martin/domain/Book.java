package com.martin.domain;

public class Book {
    private final int id;
    private final Author author;
    private final Genre genre;

    public Book(int id, Author author, Genre genre) {
        this.id = id;
        this.author = author;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }
}
