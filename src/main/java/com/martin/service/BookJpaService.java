package com.martin.service;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Comment;
import com.martin.domain.Genre;
import com.martin.repository.BookRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookJpaService implements BookService {

    private final String ERROR_STRING = "Операция с объектом %s не выполнена";
    private final String DUPLICATE_ERROR_STRING = "Запись %s существует";
    private final String EMPTY_RESULT_BY_ID_ERROR_STRING = "Объект %s c id %d не найден";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;


    public BookJpaService(BookRepository bookRepository,
                          AuthorService authorService,
                          GenreService genreService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public Book add(String title, int authorId, int genreId) throws Exception {
        Book book = new Book(title, authorService.findById(authorId), genreService.findById(genreId));
        book = add(book);
        return book;
    }

    @Override
    public Book add(String authorTitle, String authorFirsname, String authorLastname, String genreTitle) throws Exception {
        Author addedAuthor = authorService.add(authorFirsname, authorLastname);
        Genre addedGenre = genreService.add((genreTitle));
        Book book = new Book(authorTitle, addedAuthor, addedGenre);
        book = add(book);
        return book;
    }

    private Book add(Book book) throws Exception {
        try{
            book = bookRepository.insert(book);
        }
        catch (DataIntegrityViolationException exception) {
            String causeMsg= exception.getCause().getCause().getMessage();
            if(causeMsg.contains("Нарушение уникального индекса или первичного ключ"))
                throw new Exception(String.format(DUPLICATE_ERROR_STRING, book));
            else
                throw new Exception(String.format(ERROR_STRING, book));
        }
        return book;
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
        catch (DataIntegrityViolationException exception) {
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
        catch (DataIntegrityViolationException exception) {
            throw new Exception(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
        return book;
    }

    @Override
    public List<Comment> getComments(long id) {
        return bookRepository.getComments(id);
    }

    @Override
    public Book update(long bookId, String title) throws Exception {
        Book currentBook = findById(bookId);
        Book newBook = new Book(title, currentBook.getAuthor(), currentBook.getGenre());
        try{
            bookRepository.update(bookId, newBook);
        }
        catch (DataIntegrityViolationException exception) {
            throw new Exception(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
        return null;
    }

    @Override
    public Book update(long id, String bookTitle, int authorId, int genreId) throws Exception {
        Book newBook = null;//new Book(bookTitle, findAuthorById(authorId), findById(genreId));
        int amountUpdated;
        try{
            newBook = bookRepository.update(id, newBook);
        }
        catch (DataIntegrityViolationException exception) {
            throw new Exception(String.format(ERROR_STRING, Book.class.getSimpleName()));
        }
        return newBook;
    }

    @Override
    public void delete(long id) throws Exception {
        try {
            bookRepository.delete(id);
        }
        catch (DataIntegrityViolationException exception) {
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
    }

}
