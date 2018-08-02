package com.martin.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "authors")
public class Author  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstname;

    private String lastname;

    public Author() {
    }

    public Author(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    @Override
    public String toString() {
        return  "Автор (" + id + ") " + firstname +  " " + lastname ;
    }

}
