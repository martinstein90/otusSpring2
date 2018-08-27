package com.martin.servlet;

import com.martin.domain.Author;

public class AuthorDto {

    private String id;
    private String firstname;
    private String lastname;

    public AuthorDto(String id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static Author toDomainObject(AuthorDto dto) {
        return new Author(dto.getFirstname(), dto.getLastname());
    }

    public static AuthorDto toDataTransferObject(Author author) {
        return new AuthorDto(author.getId().toString(), author.getFirstname(), author.getLastname());
    }

    @Override
    public String toString() {
        return "AuthorDto{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
