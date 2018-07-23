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
import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.length;

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
        try{
            dao.insert(author);
        }
        catch (DuplicateKeyException exception){
            throw new Exception(String.format(DUPLICATE_ERROR_STRING, author));
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, author));
        }
    }

    @Override
    public void addGenre(String title) throws Exception {
        Genre genre = new Genre("Ужасы");
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
    }


    @Override
    public void addBook(String title, String firsname, String lastname, String titleGenre) {

    }

    @Override
    public void getAuthorsCount() {
    }

    @Override
    public void getGenresCount() {

    }

    @Override
    public void getABooksCount() {

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
    public void findAuthor(String authorFirstname, String authotLastname) {

    }

    @Override
    public void findGenre(String genreTitle) {

    }

    @Override
    public void findBookByTitle(String bookTitle) {

    }

    @Override
    public void findBooksByAuthor(int authorId) {

    }

    @Override
    public void findBooksByGenre(int genreId) {

    }

    @Override
    public void findAuthorById(int authorId) {

    }

    @Override
    public void findGenreById(int genreId) {

    }

    @Override
    public void findBookById(int bookId) {

    }

    @Override
    public void updateAuthor(int oldAuthorId, String authorFirstname, String authotLastname) {

    }

    @Override
    public void updateGenre(int oldGenreId, String genreTitle) {

    }

    @Override
    public void updateBook(int oldBookId, String bookTitle) {

    }

    @Override
    public void updateBook(int oldBookId, String bookTitle, int authorId, int genreId) {

    }

    @Override
    public void deleteBook(int deletedBookId) {

    }

    @Override
    public void deleteAuthor(int deleteAuthor) {

    }

    @Override
    public void deleteGenre(int deletedGenreId) {

    }
}
