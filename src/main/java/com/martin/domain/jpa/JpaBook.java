package com.martin.domain.jpa;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.martin.domain.jpa.JpaBook.COLLECTION_TITLE;

@Entity
@Table(name = COLLECTION_TITLE)
@Data @NoArgsConstructor @EqualsAndHashCode(exclude = {"id"})
public class JpaBook {

    public static final String COLLECTION_TITLE = "books";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_AUTHOR_ID = "author_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = FIELD_TITLE, length = 32)
    private String title;

    @ManyToOne
    @JoinColumn(name = FIELD_AUTHOR_ID)
    private JpaAuthor author;

    public JpaBook(String title, JpaAuthor author) {
        this.title = title;
        this.author = author;
    }
}