package com.martin.domain;

import javax.persistence.*;

@Entity
@Table(name = "genres")
public class Genre  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    private String title;

    public Genre(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Жанр (" + id + ") " + title ;
    }
}
