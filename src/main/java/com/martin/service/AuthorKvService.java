package com.martin.service;

import com.google.common.collect.Lists;
import com.martin.caching.Cachable;
import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.repository.AuthorRepository;
import org.bson.types.ObjectId;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.martin.caching.Cachable.Operation.*;
import static com.martin.service.Helper.*;

@Service
public class AuthorKvService implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorKvService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author add(String firsname, String lastname) throws Exception {
        Author author = new Author(firsname, lastname);
        try {
            authorRepository.save(author);
        }
        catch(DataAccessException exception) {
            handlerException(exception, author.toString()); //Todo вот такая обработка ошибки....
        }
        return author;
    }

    @Override
    public long getCount() {
        return authorRepository.count();
    }

    @Cachable(target = Author.class, operation = ADD, disable = true)
    @Override
    public List<Author> getAll(int page, int amountByOnePage) {
        return authorRepository.findAll(PageRequest.of(page,amountByOnePage)).getContent();
    }

    @Cachable(target = Author.class, operation = GET, disable = true)
    @Override
    public Author findById(ObjectId id) {
        return authorRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Author.class.getSimpleName(), id)));
    }

    @Override
    public List<Author> find(String frstname, String lastname) throws Exception {
        List<Author> authors = null;
        try {
            authors = Lists.newArrayList(authorRepository.findByFirstnameOrLastname(frstname, lastname));
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, Author.class.getSimpleName());
        }
        return authors;
    }

    @Override
    public Author update(ObjectId id, String firstname, String lastname) throws Exception {
        Author author = authorRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Author.class.getSimpleName(), id)));
        if(firstname!= null)
            author.setFirstname(firstname);
        if(lastname!=null)
            author.setLastname(lastname);
        try {
            authorRepository.save(author);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, Author.class.getSimpleName());
        }
        return author;
   }

    public void delete(ObjectId id) throws Exception {
        try {
            authorRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, Author.class.getSimpleName());
        }
    }

    @Override
    public void deleteAll() {
        authorRepository.deleteAll();
    }
}
