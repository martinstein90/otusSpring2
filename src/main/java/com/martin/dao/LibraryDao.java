package com.martin.dao;

import com.martin.domain.Author;
import com.martin.domain.Book;

import java.util.List;

public interface LibraryDao {
    void insertAuthor(Author author);

    int getAuthorCount();
    List<Author> getAllAuthors(int page, int amountAuthorsByOnePage);
    Author findAuthorById(int authorId);
    List<Author> findAuthorByNames(String firstname, String lastname);

    void updateAuthor(int idOldAuthor, Author newAuthor);

    void deleteAuthor(int idDeletedAuthor);
}
