package com.martin.service;

import com.google.common.collect.Lists;
import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Comment;
import com.martin.domain.Genre;
import com.martin.repository.BookRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.martin.service.Helper.EMPTY_RESULT_BY_ID_ERROR_STRING;
import static com.martin.service.Helper.handlerException;

@Service
public class BookJpaService implements BookService
{

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
            bookRepository.save(book);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, book.toString());
        }
        return book;
    }

    @Override
    public long getCount() {
        return bookRepository.count();
    }

    @Override
    public List<Book> getAll(int page, int amountByOnePage) {
        return bookRepository.findAll(PageRequest.of(page,amountByOnePage)).getContent();

    }

    @Override
    public List<Book> findByTitle(String subTitle) throws Exception {
        List<Book> books = null;
        try {
            books = Lists.newArrayList(bookRepository.findByTitleLike("%" + subTitle + "%" ));
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, Book.class.getSimpleName());
        }

        return books;
    }

    @Override
    public Book findById(long id) throws Exception {
        Optional<Book> byId = bookRepository.findById(id);
        if(!byId.isPresent())
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Book.class.getSimpleName(), id));
        return byId.get();
    }


    @Override
    public List<Comment> getComments(long id) {
        return Lists.newArrayList(bookRepository.getComments(id));
    }

    @Override
    public Book update(long id, String title) throws Exception {
        Book book = bookRepository.findById(id).get();
        if(title!= null)
            book.setTitle(title);
        try {
            bookRepository.save(book);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, Book.class.getSimpleName());
        }
        return book;
    }

    @Override
    public Book update(long id, String bookTitle, int authorId, int genreId) throws Exception {
        Book book = new Book(bookTitle, authorService.findById(authorId), genreService.findById(genreId));
        if(bookTitle!= null)
            book.setTitle(bookTitle);
        try{
            bookRepository.save(book);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, Book.class.getSimpleName());
        }
        return book;
    }

    @Override
    public void delete(long id) throws Exception {
        try {
            bookRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, Book.class.getSimpleName());
        }
    }

    @Override
    public void deleteAll() {
        bookRepository.deleteAll();
    }
}
