package com.martin.service.mongo;


import com.google.common.collect.Lists;

import com.martin.domain.mongo.MongoAuthor;
import com.martin.repository.mongo.MongoAuthorRepository;
import org.bson.types.ObjectId;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.martin.service.mongo.Helper.EMPTY_RESULT_BY_ID_ERROR_STRING;
import static com.martin.service.mongo.Helper.handlerException;


@Service
public class MongoAuthorServiceImpl implements MongoAuthorService {

    private final MongoAuthorRepository authorRepository;

    public MongoAuthorServiceImpl(MongoAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public MongoAuthor add(String firsname, String lastname) throws Exception {
        MongoAuthor author = new MongoAuthor(firsname, lastname);
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


    @Override
    public List<MongoAuthor> getAll(int page, int amountByOnePage) {
        return authorRepository.findAll(PageRequest.of(page,amountByOnePage)).getContent();
    }

    @Override
    public MongoAuthor findById(ObjectId id) {
        return authorRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, MongoAuthor.class.getSimpleName(), id)));
    }

    @Override
    public List<MongoAuthor> find(String frstname, String lastname) throws Exception {
        List<MongoAuthor> authors = null;
        try {
            authors = Lists.newArrayList(authorRepository.findByFirstnameOrLastname(frstname, lastname));
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, MongoAuthor.class.getSimpleName());
        }
        return authors;
    }

    @Override
    public MongoAuthor update(ObjectId id, String firstname, String lastname) throws Exception {
        MongoAuthor author = authorRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, MongoAuthor.class.getSimpleName(), id)));
        if(firstname!= null)
            author.setFirstname(firstname);
        if(lastname!=null)
            author.setLastname(lastname);
        try {
            authorRepository.save(author);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, MongoAuthor.class.getSimpleName());
        }
        return author;
    }

    public void delete(ObjectId id) throws Exception {
        try {
            authorRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, MongoAuthor.class.getSimpleName());
        }
    }

    @Override
    public void deleteAll() {
        authorRepository.deleteAll();
    }
}