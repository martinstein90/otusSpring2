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
public class BookVkService implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;


    public BookVkService(BookRepository bookRepository,
                         AuthorService authorService,
                         GenreService genreService,
                         CommentService commentService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
    }

    @Override
    public Book add(String title, String authorId, String genreId) throws Exception {
        Book book = new Book(title, authorService.findById(authorId), genreService.findById(genreId));
        book = add(book);
        return book;
    }

    @Override
    public Book add(String authorTitle, String authorFirsname, String authorLastname, String genreTitle) throws Exception {
//        Author addedAuthor = authorService.add(authorFirsname, authorLastname);
//        Genre addedGenre = genreService.add((genreTitle));
//        Book book = new Book(authorTitle, addedAuthor, addedGenre);
//        book = add(book);
//        return book;
        return null;
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
    public Book findById(String id) throws Exception {
        Book byId = bookRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Book.class.getSimpleName(), id)));
        return byId;
    }

    @Override
    public List<Book> findByTitle(String subTitle) throws Exception {
        List<Book> books = null;
        try {
            books = Lists.newArrayList(bookRepository.findByTitleContaining(subTitle));
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, Book.class.getSimpleName());
        }
        return books;
    }

    @Override
    public List<Book> findByAuthor(String authorId) throws Exception {
        return Lists.newArrayList(bookRepository.findByAuthor(authorId));
    }

    @Override
    public List<Book> findByGenre(String genreId) throws Exception {
        return Lists.newArrayList(bookRepository.findByGenre(genreId));
    }

    @Override
    public void addComments(String bookId, String commentId) throws Exception {
        Comment commentById = commentService.findById(commentId);
        bookRepository.addComment(bookId, commentById);
    }

    @Override
    public List<Comment> getComments(String id) {
        //return Lists.newArrayList(bookRepository.getComments(id));
        return null;
    }

    @Override
    public Book update(String id, String title) throws Exception {
        Book book = bookRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Book.class.getSimpleName(), id)));
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
    public Book update(String id, String bookTitle, String authorId, String genreId) throws Exception {
        Book book = bookRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Book.class.getSimpleName(), id)));
//        if(bookTitle!= null)
//            book.setTitle(bookTitle);
//        if(authorId != 0)
//            book.setAuthor(authorService.findById(authorId));
//        if(genreId != 0)
//            book.setGenre(genreService.findById(genreId));
//        try{
//            bookRepository.save(book);
//        }
//        catch (DataIntegrityViolationException exception) {
//            handlerException(exception, Book.class.getSimpleName());
//        }
        return book;
    }

    @Override
    public void delete(String id) throws Exception {
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
