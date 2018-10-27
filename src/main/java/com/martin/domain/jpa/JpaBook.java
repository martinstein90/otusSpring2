package com.martin.domain.jpa;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "books")
@Data @NoArgsConstructor  @EqualsAndHashCode(exclude = {"id"})
public class JpaBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", length = 32)
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private JpaAuthor author;

    public JpaBook(String title, JpaAuthor author) {
        this.title = title;
        this.author = author;
    }
}