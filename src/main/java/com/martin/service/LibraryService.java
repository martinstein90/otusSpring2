package com.martin.service;

public interface LibraryService {

    void addAuthor(String firsname, String lastname) throws Exception;
    void addGenre(String title) throws Exception;
    void addBook(String title, int authorId, int genreId) throws Exception;
    void addBook(String title, String firsname, String lastname, String titleGenre) throws Exception;

    void getAuthorsCount();
    void getGenresCount();
    void getBooksCount();

    void getAllAuthors();
    void getAllGenres();
    void getAllBooks();

    void findAuthor (String authorFirstname, String authotLastname) throws Exception;
    void findGenre  (String genreTitle) throws Exception;
    void findBookByTitle    (String bookTitle) throws Exception;
    void findBooksByAuthor  (int authorId) throws Exception;
    void findBooksByGenre   (int genreId) throws Exception;

    void findAuthorById (int authorId) throws Exception;
    void findGenreById  (int genreId) throws Exception;
    void findBookById   (int bookId) throws Exception;

    void updateAuthor   (int authorId, String authorFirstname, String authotLastname) throws Exception;
    void updateGenre    (int genreId, String genreTitle) throws Exception;
    void updateBook     (int bookId, String bookTitle) throws Exception;
    void updateBook     (int bookId, String bookTitle, int authorId, int genreId) throws Exception;

    void deleteBook     (int deletedBookId) throws Exception;
    void deleteAuthor   (int deleteAuthor) throws Exception;
    void deleteGenre    (int deletedGenreId) throws Exception;
}
