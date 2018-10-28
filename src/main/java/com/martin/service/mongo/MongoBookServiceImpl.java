package com.martin.service.mongo;


import com.google.common.collect.Lists;
import com.martin.domain.mongo.MongoAuthor;
import com.martin.domain.mongo.MongoBook;
import com.martin.repository.mongo.MongoBookRepository;
import org.bson.types.ObjectId;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.martin.service.mongo.Helper.EMPTY_RESULT_BY_ID_ERROR_STRING;
import static com.martin.service.mongo.Helper.handlerException;


@Service
public class MongoBookServiceImpl implements MongoBookService {

    private final MongoBookRepository bookRepository;
    private final MongoAuthorService authorService;

    public MongoBookServiceImpl(MongoBookRepository bookRepository,
                         MongoAuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @Override
    public MongoBook add(String title, ObjectId authorId) throws Exception {
        MongoBook book = new MongoBook(title, authorService.findById(authorId));
        book = add(book);
        return book;
    }

    @Override
    public MongoBook add(String authorTitle, String authorFirsname, String authorLastname) throws Exception {
        MongoAuthor addedAuthor = authorService.add(authorFirsname, authorLastname);
        MongoBook book = new MongoBook(authorTitle, addedAuthor);
        book = add(book);
        return book;
    }

    private MongoBook add(MongoBook book) throws Exception {
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
    public List<MongoBook> getAll(int page, int amountByOnePage) {
        return bookRepository.findAll(PageRequest.of(page,amountByOnePage)).getContent();

    }

    @Override
    public MongoBook findById(ObjectId id) throws Exception {
        MongoBook byId = bookRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, MongoBook.class.getSimpleName(), id)));
        return byId;
    }

    @Override
    public List<MongoBook> findByTitle(String subTitle) throws Exception {
        List<MongoBook> books = null;
        try {
            books = Lists.newArrayList(bookRepository.findByTitleContaining(subTitle));
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, MongoBook.class.getSimpleName());
        }
        return books;
    }

    @Override
    public List<MongoBook> findByAuthor(ObjectId authorId) throws Exception {
        return Lists.newArrayList(bookRepository.findByAuthor(authorId));
    }


    @Override
    public MongoBook update(ObjectId id, String title) throws Exception {
        MongoBook book = bookRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, MongoBook.class.getSimpleName(), id)));
        if(title!= null)
            book.setTitle(title);
        try {
            bookRepository.save(book);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, MongoBook.class.getSimpleName());
        }
        return book;
    }

    @Override
    public MongoBook update(ObjectId id, String bookTitle, ObjectId authorId) throws Exception {
        MongoBook book = bookRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, MongoBook.class.getSimpleName(), id)));
        if(bookTitle!= null)
            book.setTitle(bookTitle);
        if(authorId != null)
            book.setAuthor(authorService.findById(authorId));

        try{
            bookRepository.save(book);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, MongoBook.class.getSimpleName());
        }
        return book;
    }

    @Override
    public void delete(ObjectId id) throws Exception {
        try {
            bookRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, MongoBook.class.getSimpleName());
        }
    }

    @Override
    public void deleteAll() {
        bookRepository.deleteAll();
    }
}