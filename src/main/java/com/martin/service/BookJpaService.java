package com.martin.service;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import com.martin.repository.BookRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookJpaService implements BookService {

    private final String ERROR_STRING = "Операция с объектом %s не выполнена";
    private final String DUPLICATE_ERROR_STRING = "Запись %s существует";
    private final String EMPTY_RESULT_BY_ID_ERROR_STRING = "Объект %s c id %d не найден";

    private final BookRepository bookRepository;

    public BookJpaService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void addBook(String title, int authorId, int genreId) throws Exception {
//        Book book = new Book(title, findAuthorById(authorId), findGenreById(genreId));
//        try{
//            bookRepository.insert(book);
//        }
//        catch (DuplicateKeyException exception){
//            throw new Exception(String.format(DUPLICATE_ERROR_STRING, book));
//        }
//        catch(DataAccessException exception) {
//            System.out.println(exception.getMessage());
//            throw new Exception(String.format(ERROR_STRING, book));
//        }
    }

    @Override
    public void addBook(String authorTitle, String authorFirsname, String authorLastname, String genreTitle) throws Exception {
        //addAuthor(authorFirsname, authorLastname);
        Author foundAuthor = null;//(findAuthor(authorFirsname, authorLastname)).get(0);

        //addGenre(genreTitle);
        Genre foundGenre = null;//(findGenre(genreTitle)).get(0);

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
    public long getCount() {
        return bookRepository.getCount();
    }

    @Override
    public List<Book> getAll(int page, int amountByOnePage) {
        return bookRepository.getAll(page, amountByOnePage);
    }

    @Override
    public List<Book> findByTitle(String title) throws Exception {
        Book book = new Book(title, null, null);
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
    public Book findById(long id) throws Exception {
        Book book;
        try {
            book = bookRepository.findById(id);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Book.class.getSimpleName(), id));
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
        return book;
    }

    @Override
    public void updateBook(long bookId, String title) throws Exception {
        Book currentBook =null;// findBookById(bookId);
        Book newBook = new Book(title, currentBook.getAuthor(), currentBook.getGenre());
        try{
            bookRepository.update(bookId, newBook);
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
    }

    @Override
    public void updateBook(long id, String bookTitle, int authorId, int genreId) throws Exception {
        Book newBook = null;//new Book(bookTitle, findAuthorById(authorId), findById(genreId));
        int amountUpdated;
        try{
            amountUpdated = bookRepository.update(id, newBook);
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }

        if(amountUpdated == 0)
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Book.class.getSimpleName(), genreId));
    }

    @Override
    public void deleteBook(long id) throws Exception {
        try {
            bookRepository.delete(id);
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
    }

}
