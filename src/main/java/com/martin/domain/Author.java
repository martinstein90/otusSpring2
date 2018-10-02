package com.martin.domain;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.*;

import static com.martin.domain.Author.COLLECTION_TITLE;

@Data
@ToString()
@EqualsAndHashCode(exclude = {"id"})
public class Author {

    public static final String COLLECTION_TITLE = "authors";
    public static final String FIELD_FIRSTNAME = "Firstname";
    public static final String FIELD_LASTNAME = "Lastname";

    @Id
    @Getter
    private ObjectId id;

    @Field(value = FIELD_FIRSTNAME)
    @Getter
    @Setter
    private String firstname;

    @Field(value = FIELD_LASTNAME)
    @Getter
    @Setter
    private String lastname;

    public Author() {
    }

    public Author(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

}
