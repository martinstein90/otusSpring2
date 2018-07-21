package com.martin.service;

public interface LibraryService {

    void addAuthor(String firsname, String lastname) throws Exception;
    void addGenre(String title) throws Exception;
    void addBook(String title, int authorId, int genreId) throws Exception;
    void addBook(String title, String firsname, String lastname, int genreId);
    void addBook(String title, int authorId, String titleGenre);
    void addBook(String title, String firsname, String lastname, String titleGenre);
}
