package com.martin.domain.mongo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import static com.martin.domain.mongo.MongoAuthor.COLLECTION_TITLE;

@CompoundIndexes({
        @CompoundIndex(unique = true, name = "unicNamesAuthor", def="{'Firstname' : 1, 'Lastname': 1}")})
@Document(collection=COLLECTION_TITLE)
@Data @NoArgsConstructor @EqualsAndHashCode(exclude = "id")
public class MongoAuthor {

    public static final String COLLECTION_TITLE = "authors";
    public static final String FIELD_FIRSTNAME = "Firstname";
    public static final String FIELD_LASTNAME = "Lastname";

    @Id
    private ObjectId id;

    @Field(value = FIELD_FIRSTNAME)
    private String firstname;

    @Field(value = FIELD_LASTNAME)
    private String lastname;

    public MongoAuthor(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
