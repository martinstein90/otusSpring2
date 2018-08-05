package com.martin.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genres")
public class Genre  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @OneToMany(mappedBy="genre", fetch=FetchType.EAGER)
    private List<Book> books = new ArrayList<>();

    public Genre() {
    }

    public Genre(String title) {
        this.title = title;
    }

    public List<Book> getBooks() {
        return books;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Жанр (" + id + ") " + title ;
    }
}
