package com.martin.service;

public interface LibraryService {

    void addAuthor(String firsname, String lastname) throws Exception;
    void addGenre(String title) throws Exception;
    void addBook(String title, int authorId, int genreId) throws Exception;
    void addBook(String title, String firsname, String lastname, String titleGenre);

    void getAuthorsCount();
    void getGenresCount();
    void getABooksCount();

    void getAllAuthors();
    void getAllGenres();
    void getAllBooks();

    void findAuthor (String authorFirstname, String authotLastname);
    void findGenre  (String genreTitle);
    void findBookByTitle    (String bookTitle);
    void findBooksByAuthor  (int authorId);
    void findBooksByGenre   (int genreId);

    void findAuthorById (int authorId);
    void findGenreById  (int genreId);
    void findBookById   (int bookId);

    void updateAuthor   (int oldAuthorId, String authorFirstname, String authotLastname);
    void updateGenre    (int oldGenreId, String genreTitle);
    void updateBook     (int oldBookId, String bookTitle);
    void updateBook     (int oldBookId, String bookTitle, int authorId, int genreId);

    void deleteBook     (int deletedBookId);
    void deleteAuthor   (int deleteAuthor);
    void deleteGenre    (int deletedGenreId);
}
