package com.martin.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.*;

import static com.martin.domain.Author.COLLECTION_TITLE;

@CompoundIndexes({
        @CompoundIndex(unique = true, name = "unicNamesAuthor", def="{'Firstname' : 1, 'Lastname': 1}")
})
@Document(collection=COLLECTION_TITLE)
public class Author implements Storable{

    public static final String COLLECTION_TITLE = "authors";
    public static final String FIELD_FIRSTNAME = "Firstname";
    public static final String FIELD_LASTNAME = "Lastname";

    @Id
    private ObjectId id;

    @Field(value = FIELD_FIRSTNAME)
    private String firstname;

    @Field(value = FIELD_LASTNAME)
    private String lastname;

    public Author() {
    }

    public Author(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @Override
    public ObjectId getId() {
        return id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(firstname, author.firstname) &&
                Objects.equals(lastname, author.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname);
    }
}
