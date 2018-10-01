package com.martin.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.martin.domain.Book.COLLECTION_TITLE;
import static com.martin.domain.Book.FIELD_TITLE;

@CompoundIndexes({
        @CompoundIndex(unique = true, name = "unicTitleBook", def="{'"+ FIELD_TITLE + "' : 1}")
})
@Document(collection = COLLECTION_TITLE)
public class Book implements Storable{

    public static final String COLLECTION_TITLE = "books";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_AUTHOR = "author";
    public static final String FIELD_GENRE = "genre";
    public static final String FIELD_COMMENTS = "comments";

    @Id
    private ObjectId id;

    @Field(FIELD_TITLE)
    private String title;

    @Field(FIELD_AUTHOR)
    @DBRef
    private Author author;

    @Field(FIELD_GENRE)
    @DBRef
    private Genre genre;

    @Field(FIELD_COMMENTS)
    @DBRef
    private List<Comment> comments = new ArrayList<>();

    public Book() {
    }

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public ObjectId getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setGenre(Genre genre) {
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

    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Книга (" + id + ") " + title + " " + author + " " + genre + " "+ comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(genre, book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, genre);
    }
}
