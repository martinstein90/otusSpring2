package com.martin.service;

import com.martin.dao.LibraryDao;
import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import static com.martin.helper.Ansi.ANSI_BLUE;
import static com.martin.helper.Ansi.ANSI_RED;
import static com.martin.helper.Ansi.ANSI_RESET;

@Service
public class LidraryJdbcService implements LibraryService {

    private final LibraryDao dao;

    private final String WORKING_COLOR = ANSI_RED;
    private final String RESET_COLOR = ANSI_RESET;

    private final String ERROR_STRING = "Операция с объектов %s не выполнена";
    private final String DUPLICATE_ERROR_STRING = "Запись %s существует";
    private final String EMPTY_RESULT_BY_ID_ERROR_STRING = "Объект c id %d не найден";

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
        Author author = null;
        try {
            author = dao.findById(Author.class, authorId);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, authorId));
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new Exception(String.format(ERROR_STRING, author));
        }

        Genre genre = dao.findById(Genre.class, genreId);

        Book book = new Book(title, author, genre);
        try{
            dao.insert(book);
        }
        catch (DuplicateKeyException exception){
            throw new Exception(String.format(DUPLICATE_ERROR_STRING, author));
        }
        catch(DataAccessException exception) {
            System.out.println(WORKING_COLOR + "Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage() + ANSI_RESET);
            throw new RuntimeException(ERROR_STRING);
        }
    }

    @Override
    public void addBook(String title, String firsname, String lastname, int genreId) {

    }

    @Override
    public void addBook(String title, int authorId, String titleGenre) {

    }

    @Override
    public void addBook(String title, String firsname, String lastname, String titleGenre) {

    }
}
