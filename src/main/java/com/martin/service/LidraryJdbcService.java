package com.martin.service;

import com.martin.dao.LibraryDao;
import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import com.martin.domain.Storable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static com.martin.helper.Ansi.ANSI_BLUE;
import static com.martin.helper.Ansi.ANSI_RED;
import static com.martin.helper.Ansi.ANSI_RESET;

@Service
public class LidraryJdbcService implements LibraryService {

    private final LibraryDao dao;

    private final String WORKING_COLOR = ANSI_RED;
    private final String RESET_COLOR = ANSI_RESET;

    private final String ERROR_STRING = "Операция с объектом %s не выполнена";
    private final String DUPLICATE_ERROR_STRING = "Запись %s существует";
    private final String EMPTY_RESULT_BY_ID_ERROR_STRING = "Объект %s c id %d не найден";

    public LidraryJdbcService(LibraryDao dao) {
        this.dao = dao;
    }

    @Override
    public void addAuthor(String firsname, String lastname) throws Exception {
        Author author = new Author(firsname, lastname);
        try {
            dao.insert(author);
        }
        catch (DuplicateKeyException exception){
            throw new Exception(String.format(DUPLICATE_ERROR_STRING, author));
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, author));
        }
        System.out.println("Автор добавлен");
    }

    @Override
    public void addGenre(String title) throws Exception {
        Genre genre = new Genre(title);
        try{
            dao.insert(genre);
        }
        catch (DuplicateKeyException exception){
            throw new Exception(String.format(DUPLICATE_ERROR_STRING, genre));
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, genre));
        }
        System.out.println("Жанр добавен");
    }

    @Override
    public void addBook(String title, int authorId, int genreId) throws Exception {
        Author author;
        try {
            author = dao.findById(Author.class, authorId);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Author.class.getSimpleName(), authorId));
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }

        Genre genre;
        try {
            genre = dao.findById(Genre.class, genreId);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Genre.class.getSimpleName(), genreId));
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }

        Book book = new Book(title, author, genre);
        try{
            dao.insert(book);
        }
        catch (DuplicateKeyException exception){
            throw new Exception(String.format(DUPLICATE_ERROR_STRING, book));
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, book));
        }

        System.out.println("Книга добавлена");
    }

    @Override
    public void addBook(String authorTitle, String authorFirsname, String authorLastname, String genreTitle) throws Exception {
        Author author = new Author(authorFirsname, authorLastname);
        try {
            dao.insert(author);
        }
        catch (DuplicateKeyException exception){
            throw new Exception(String.format(DUPLICATE_ERROR_STRING, author));
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, author));
        }

        Genre genre = new Genre(genreTitle);
        try{
            dao.insert(genre);
        }
        catch (DuplicateKeyException exception){
            throw new Exception(String.format(DUPLICATE_ERROR_STRING, genre));
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, genre));
        }

        Book book = new Book(authorTitle, author, genre);
        try{
            dao.insert(book);
        }
        catch (DuplicateKeyException exception){
            throw new Exception(String.format(DUPLICATE_ERROR_STRING, book));
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, book));
        }

        System.out.println("Книга добавлена");
    }

    @Override
    public void getAuthorsCount() {
        System.out.println("Кол-во авторов:" + dao.getCount(Author.class));
    }

    @Override
    public void getGenresCount() {
        System.out.println("Кол-во жанров:" + dao.getCount(Genre.class));
    }

    @Override
    public void getBooksCount() {
        System.out.println("Кол-во книг" + dao.getCount(Book.class));
    }

    @Override
    public void getAllAuthors() {
        int amount = dao.getCount(Author.class);
        int amountByOnePage = 5;
        int page, pages = (int) Math.ceil((float)amount / amountByOnePage);
        for (page = 0; page < pages; page++) {
            List<Author> all = dao.getAll(Author.class, page + 1, amountByOnePage);
            System.out.println("Page " + (page +1));
            for (Author a : all) {
                System.out.println(a);
            }
        }
    }

    @Override
    public void getAllGenres() {
        int amount = dao.getCount(Genre.class);
        int amountByOnePage = 5;
        int page, pages = (int) Math.ceil((float)amount / amountByOnePage);
        for (page = 0; page < pages; page++) {
            List<Genre> all = dao.getAll(Genre.class, page + 1, amountByOnePage);
            System.out.println("Page " + (page +1));
            for (Genre g : all) {
                System.out.println(g);
            }
        }
    }

    @Override
    public void getAllBooks() {
        int amount = dao.getCount(Book.class);
        int amountByOnePage = 5;
        int page, pages = (int) Math.ceil((float)amount / amountByOnePage);
        for (page = 0; page < pages; page++) {
            List<Book> all = dao.getAll(Book.class, page + 1, amountByOnePage);
            System.out.println("Page " + (page + 1));
            for (Book b : all) {
                System.out.println(b);
            }
        }
    }

    @Override
    public void findAuthor(String authorFirstname, String authotLastname) throws Exception {
        Author author = new Author(authorFirstname, authotLastname);
        List<Author> authors;
        try {
            authors = dao.find(author);
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }

        System.out.println("Найденные авторы:");
        for (Author a: authors) {
            System.out.println(a);
        }
    }

    @Override
    public void findGenre(String genreTitle) throws Exception {
        Genre genre = new Genre(genreTitle);
        List<Genre> genres;
        try {
            genres = dao.find(genre);
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }

        System.out.println("Найденные жанры:");
        for (Genre g: genres) {
            System.out.println(g);
        }
    }

    @Override
    public void findBookByTitle(String bookTitle) throws Exception {
        Book book = new Book(bookTitle, null, null);
        List<Book> books;
        try {
            books = dao.find(book);
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }

        System.out.println("Найденные книги:");
        for (Book b: books) {
            System.out.println(b);
        }
    }

    @Override
    public void findBooksByAuthor(int authorId) throws Exception {
        Author author;
        try {
            author = dao.findById(Author.class, authorId);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Author.class.getSimpleName(), authorId));
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }

        Book book = new Book(null, author, null);
        List<Book> books;
        try {
            books = dao.find(book);
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }

        System.out.println("Найденные книги:");
        for (Book b: books) {
            System.out.println(b);
        }
    }

    @Override
    public void findBooksByGenre(int genreId) throws Exception {
        Genre genre;
        try {
            genre = dao.findById(Genre.class, genreId);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Genre.class.getSimpleName(), genreId));
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }

        Book book = new Book(null, null, genre);
        List<Book> books;
        try {
            books = dao.find(book);
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }

        System.out.println("Найденные книги:");
        for (Book b: books) {
            System.out.println(b);
        }
    }

    @Override
    public void findAuthorById(int authorId) throws Exception {
        Author author;
        try {
            author = dao.findById(Author.class, authorId);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Author.class.getSimpleName(), authorId));
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
        System.out.println("Автор найден: " + author);
    }

    @Override
    public void findGenreById(int genreId) throws Exception {
        Genre genre;
        try {
            genre = dao.findById(Genre.class, genreId);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Genre.class.getSimpleName(), genreId));
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }

        System.out.println("Жанр найден: " + genre);
    }

    @Override
    public void findBookById(int bookId) throws Exception {
        Book book;
        try {
            book = dao.findById(Book.class, bookId);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Book.class.getSimpleName(), bookId));
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }

        System.out.println("Книга найдена: " + book);
    }

    @Override
    public void updateAuthor(int authorId, String authorFirstname, String authotLastname) throws Exception {
        Author author = new Author(authorFirstname, authotLastname);
        int amountUpdated;
        try {
            amountUpdated = dao.update(authorId, author);
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }

        if(amountUpdated == 0)
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Author.class.getSimpleName(), authorId));

        System.out.println("Автор обнавлен");
    }

    @Override
    public void updateGenre(int genreId, String genreTitle) throws Exception {
        Genre genre = new Genre(genreTitle);
        int amountUpdated;
        try {
            amountUpdated = dao.update(genreId, genre);
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }

        if(amountUpdated == 0)
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Genre.class.getSimpleName(), genreId));

        System.out.println("Жанр обнавлен");
    }

    @Override
    public void updateBook(int bookId, String bookTitle) throws Exception {
        Book currentBook;
        try {
            currentBook = dao.findById(Book.class, bookId);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Book.class.getSimpleName(), bookId));
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }

        Book newBook = new Book(bookTitle, currentBook.getAuthor(), currentBook.getGenre());
        try{
            dao.update(bookId, newBook);
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }

        System.out.println("Жанр обнавлен");
    }

    @Override
    public void updateBook(int bookId, String bookTitle, int authorId, int genreId) throws Exception {
        Author author;
        try {
            author = dao.findById(Author.class, authorId);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Author.class.getSimpleName(), authorId));
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }

        Genre genre;
        try {
            genre = dao.findById(Genre.class, genreId);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Genre.class.getSimpleName(), genreId));
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }

        Book newBook = new Book(bookTitle, author, genre);
        int amountUpdated;
        try{
            amountUpdated = dao.update(bookId, newBook);
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }

        if(amountUpdated == 0)
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Book.class.getSimpleName(), genreId));

        System.out.println("Книга обнавлен");
    }

    @Override
    public void deleteBook(int deletedBookId) throws Exception {
        int amountDeleted;
        try {
            amountDeleted = dao.delete(Book.class, deletedBookId);
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }

        if(amountDeleted == 0)
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Book.class.getSimpleName(), deletedBookId));

        System.out.println("Книга удалена");
    }

    @Override
    public void deleteAuthor(int deleteAuthor) throws Exception {
        int amountDeleted;
        try {
            amountDeleted = dao.delete(Author.class, deleteAuthor);
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }

        if(amountDeleted == 0)
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Author.class.getSimpleName(), deleteAuthor));

        System.out.println("Автор удален");
    }

    @Override
    public void deleteGenre(int deletedGenreId) throws Exception {
        int amountDeleted;
        try {
            amountDeleted = dao.delete(Genre.class, deletedGenreId);
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }

        if(amountDeleted == 0)
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Genre.class.getSimpleName(), deletedGenreId));

        System.out.println("Жанр удален");
    }
}
