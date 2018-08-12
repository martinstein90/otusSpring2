package com.martin.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "genres")
public class Genre implements Storable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", length = 32)
    private String title;

    @OneToMany(mappedBy="genre", fetch=FetchType.LAZY)
    private List<Book> books;

    public Genre() {
    }

    public Genre(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public List<Book> getBooks() {
        return books;
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
