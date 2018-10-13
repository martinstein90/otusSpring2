package com.martin.domain;

import lombok.*;

import javax.persistence.*;

import static com.martin.domain.Author.COLLECTION_TITLE;

@Data @NoArgsConstructor @ToString @EqualsAndHashCode(exclude = {"id"})
@Entity @Table(name = COLLECTION_TITLE)
public class Author {

    public static final String COLLECTION_TITLE = "authors";
    public static final String FIELD_FIRSTNAME = "Firstname";
    public static final String FIELD_LASTNAME = "Lastname";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private long id;

    @Getter @Setter
    @Column(name = FIELD_FIRSTNAME)
    private String firstname;

    @Getter @Setter
    @Column(name = FIELD_LASTNAME)
    private String lastname;

    public Author(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
