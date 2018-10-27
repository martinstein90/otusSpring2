package com.martin.service.jpa;


import com.google.common.collect.Lists;
import com.martin.domain.jpa.JpaAuthor;
import com.martin.domain.jpa.JpaBook;
import com.martin.repository.jpa.JpaBookRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.martin.service.jpa.JpaHelper.EMPTY_RESULT_BY_ID_ERROR_STRING;
import static com.martin.service.jpa.JpaHelper.handlerException;

@Service
public class JpaBookServiceImpl implements JpaBookService
{

    private final JpaBookRepository bookRepository;
    private final JpaAuthorService authorService;


    public JpaBookServiceImpl(JpaBookRepository bookRepository,
                          JpaAuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @Override
    public JpaBook add(String title, long authorId) throws Exception {
        JpaBook book = new JpaBook(title, authorService.findById(authorId));
        book = add(book);
        return book;
    }

    @Override
    public JpaBook add(String authorTitle, String authorFirsname, String authorLastname) throws Exception {
        JpaAuthor addedAuthor = authorService.add(authorFirsname, authorLastname);

        JpaBook book = new JpaBook(authorTitle, addedAuthor);
        book = add(book);
        return book;
    }

    private JpaBook add(JpaBook book) throws Exception {
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
    public List<JpaBook> getAll(int page, int amountByOnePage) {
        return bookRepository.findAll(PageRequest.of(page,amountByOnePage)).getContent();

    }

    @Override
    public List<JpaBook> findByTitle(String subTitle) throws Exception {
        List<JpaBook> books = null;
        try {
            books = Lists.newArrayList(bookRepository.findByTitleContaining(subTitle));
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, JpaBook.class.getSimpleName());
        }
        return books;
    }

    @Override
    public JpaBook findById(long id) throws Exception {
        JpaBook byId = bookRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, JpaBook.class.getSimpleName(), id)));
        return byId;
    }


    @Override
    public JpaBook update(long id, String title) throws Exception {
        JpaBook book = bookRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, JpaBook.class.getSimpleName(), id)));
        if(title!= null)
            book.setTitle(title);
        try {
            bookRepository.save(book);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, JpaBook.class.getSimpleName());
        }
        return book;
    }

    @Override
    public JpaBook update(long id, String bookTitle, long authorId) throws Exception {
        JpaBook book = bookRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, JpaBook.class.getSimpleName(), id)));
        if(bookTitle!= null)
            book.setTitle(bookTitle);
        if(authorId != 0)
            book.setAuthor(authorService.findById(authorId));
        try{
            bookRepository.save(book);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, JpaBook.class.getSimpleName());
        }
        return book;
    }

    @Override
    public void delete(long id) throws Exception {
        try {
            bookRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, JpaBook.class.getSimpleName());
        }
    }

    @Override
    public void deleteAll() {
        bookRepository.deleteAll();
    }
}