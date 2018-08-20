package com.martin.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Objects;

@Document(collection="genres")
public class Genre implements Storable{

    @Id
    private String id;

    @Field("title")
    private String title;

    public Genre() {
    }

    public Genre(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Жанр (" + id + ") " + title ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(title, genre.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
