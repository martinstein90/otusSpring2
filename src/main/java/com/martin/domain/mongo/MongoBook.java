package com.martin.domain.mongo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import static com.martin.domain.mongo.MongoBook.COLLECTION_TITLE;
import static com.martin.domain.mongo.MongoBook.FIELD_TITLE;

@CompoundIndexes({
        @CompoundIndex(unique = true, name = "unicTitleBook", def="{'"+ FIELD_TITLE + "' : 1}")
})
@Document(collection = COLLECTION_TITLE)
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class MongoBook {

    public static final String COLLECTION_TITLE = "books";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_AUTHOR = "author";

    @Id
    private ObjectId id;

    @Field(FIELD_TITLE)
    private String title;

    @Field(FIELD_AUTHOR)
    @DBRef
    private MongoAuthor author;

    public MongoBook(String title, MongoAuthor author) {
        this.title = title;
        this.author = author;

    }
}
