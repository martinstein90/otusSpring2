package com.martin.service;

import com.martin.repository.AuthorRepository;
import com.martin.repository.BookRepository;
import com.martin.repository.GenreRepository;
import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryJpaService implements LibraryService {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    private final String ERROR_STRING = "Операция с объектом %s не выполнена";
    private final String DUPLICATE_ERROR_STRING = "Запись %s существует";
    private final String EMPTY_RESULT_BY_ID_ERROR_STRING = "Объект %s c id %d не найден";

    public LibraryJpaService(AuthorRepository authorRepository, GenreRepository genreRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }


    @Override
    public void addAuthor(String firsname, String lastname) throws Exception {
        Author author = new Author(firsname, lastname);
        try {
            authorRepository.insert(author);
        }
        catch (DuplicateKeyException exception){
            throw new Exception(String.format(DUPLICATE_ERROR_STRING, author));
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, author));
        }
    }

    @Override
    public void addGenre(String title) throws Exception {
        Genre genre = new Genre(title);
        try{
            genreRepository.insert(genre);
        }
        catch (DuplicateKeyException exception){
            throw new Exception(String.format(DUPLICATE_ERROR_STRING, genre));
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, genre));
        }
    }

    @Override
    public void addBook(String title, int authorId, int genreId) throws Exception {
        Book book = new Book(title, findAuthorById(authorId), findGenreById(genreId));
        try{
            bookRepository.insert(book);
        }
        catch (DuplicateKeyException exception){
            throw new Exception(String.format(DUPLICATE_ERROR_STRING, book));
        }
        catch(DataAccessException exception) {

            System.out.println(exception.getMessage());
            throw new Exception(String.format(ERROR_STRING, book));
        }
    }

    @Override
    public void addBook(String authorTitle, String authorFirsname, String authorLastname, String genreTitle) throws Exception {
        addAuthor(authorFirsname, authorLastname);
        Author foundAuthor = (findAuthor(authorFirsname, authorLastname)).get(0);

        addGenre(genreTitle);
        Genre foundGenre = (findGenre(genreTitle)).get(0);

        Book book = new Book(authorTitle, foundAuthor, foundGenre);
        try{
            bookRepository.insert(book);
        }
        catch (DuplicateKeyException exception){
            throw new Exception(String.format(DUPLICATE_ERROR_STRING, book));
        }
        catch(DataAccessException exception) {
            System.out.println(exception.getMessage());
            throw new Exception(String.format(ERROR_STRING, book));
        }
    }

    @Override
    public int getAuthorsCount() {
        return authorRepository.getCount();
    }

    @Override
    public int getGenresCount() {
        return genreRepository.getCount();
    }

    @Override
    public int getBooksCount() {
        return bookRepository.getCount();
    }

    @Override
    public List<Author> getAllAuthors(int page, int amountByOnePage) {
        return authorRepository.getAll(page, amountByOnePage);
    }

    @Override
    public List<Genre> getAllGenres(int page, int amountByOnePage) {
        return genreRepository.getAll(page, amountByOnePage);
    }

    @Override
    public List<Book> getAllBooks(int page, int amountByOnePage) {
        return bookRepository.getAll(page, amountByOnePage);
    }

    @Override
    public List<Author> findAuthor(String authorFirstname, String authotLastname) throws Exception {
        Author author = new Author(authorFirstname, authotLastname);
        List<Author> authors;
        try {
            authors = authorRepository.find(author);
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
        return authors;
    }

    @Override
    public List<Genre> findGenre(String genreTitle) throws Exception {
        Genre genre = new Genre(genreTitle);
        List<Genre> genres;
        try {
            genres = genreRepository.find(genre);
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
        return genres;
    }

    @Override
    public List<Book> findBookByTitle(String bookTitle) throws Exception {
        Book book = new Book(bookTitle, null, null);
        List<Book> books;
        try {
            books = bookRepository.find(book);
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
        return books;
    }

    @Override
    public List<Book> findBooksByAuthor(int authorId) throws Exception {
        Book book = new Book(null, findAuthorById(authorId), null);
        List<Book> books;
        try {
            books = bookRepository.find(book);
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
        return books;
    }

    @Override
    public List<Book> findBooksByGenre(int genreId) throws Exception {
        Book book = new Book(null, null, findGenreById(genreId));
        List<Book> books;
        try {
            books = bookRepository.find(book);
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
        return books;
    }

    @Override
    public Author findAuthorById(int authorId) throws Exception {
        Author author;
        try {
            author = authorRepository.findById(authorId);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Author.class.getSimpleName(), authorId));
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
        return author;
    }

    @Override
    public Genre findGenreById(int genreId) throws Exception {
        Genre genre;
        try {
            genre = genreRepository.findById(genreId);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Genre.class.getSimpleName(), genreId));
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
        return genre;
    }

    @Override
    public Book findBookById(int bookId) throws Exception {
        Book book;
        try {
            book = bookRepository.findById(bookId);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Book.class.getSimpleName(), bookId));
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
        return book;
    }

    @Override
    public void updateAuthor(int authorId, String authorFirstname, String authotLastname) throws Exception {
        Author author = new Author(authorFirstname, authotLastname);
        int amountUpdated;
        try {
            amountUpdated = authorRepository.update(authorId, author);
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }

        if(amountUpdated == 0)
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Author.class.getSimpleName(), authorId));
    }

    @Override
    public void updateGenre(int genreId, String genreTitle) throws Exception {
        Genre genre = new Genre(genreTitle);
        int amountUpdated;
        try {
            amountUpdated = genreRepository.update(genreId, genre);
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }

        if(amountUpdated == 0)
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Genre.class.getSimpleName(), genreId));
    }

    @Override
    public void updateBook(int bookId, String bookTitle) throws Exception {
        Book currentBook = findBookById(bookId);
        Book newBook = new Book(bookTitle, currentBook.getAuthor(), currentBook.getGenre());
        try{
            bookRepository.update(bookId, newBook);
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
    }

    @Override
    public void updateBook(int bookId, String bookTitle, int authorId, int genreId) throws Exception {
        Book newBook = new Book(bookTitle, findAuthorById(authorId), findGenreById(genreId));
        int amountUpdated;
        try{
            amountUpdated = bookRepository.update(bookId, newBook);
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }

        if(amountUpdated == 0)
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Book.class.getSimpleName(), genreId));
    }

    @Override
    public void deleteBook(int deletedBookId) throws Exception {
        try {
            bookRepository.delete(deletedBookId);
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
    }

    @Override
    public void deleteAuthor(int deleteAuthor) throws Exception {
        try {
            authorRepository.delete(deleteAuthor);
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
    }

    @Override
    public void deleteGenre(int deletedGenreId) throws Exception {
        try {
            genreRepository.delete(deletedGenreId);
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
    }
}
