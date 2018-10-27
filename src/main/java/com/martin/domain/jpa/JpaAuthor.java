package com.martin.domain.jpa;

import lombok.*;

import javax.persistence.*;

import java.util.List;

import static com.martin.domain.jpa.JpaAuthor.COLLECTION_TITLE;

@Data @NoArgsConstructor @EqualsAndHashCode(exclude = {"id"})
@Entity @Table(name = COLLECTION_TITLE)
public class JpaAuthor {

    public static final String COLLECTION_TITLE = "authors";
    public static final String FIELD_FIRSTNAME = "Firstname";
    public static final String FIELD_LASTNAME = "Lastname";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = FIELD_FIRSTNAME)
    private String firstname;

    @Column(name = FIELD_LASTNAME)
    private String lastname;

    @OneToMany(mappedBy="author", fetch=FetchType.LAZY)
    private List<JpaBook> books;

    public JpaAuthor(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
