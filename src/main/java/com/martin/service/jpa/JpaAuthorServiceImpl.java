package com.martin.service.jpa;

import com.google.common.collect.Lists;
import com.martin.domain.jpa.JpaAuthor;
import com.martin.domain.jpa.JpaBook;
import com.martin.repository.jpa.JpaAuthorRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.martin.service.jpa.JpaHelper.*;

@Service
public class JpaAuthorServiceImpl implements JpaAuthorService {

    private final JpaAuthorRepository authorRepository;

    public JpaAuthorServiceImpl(JpaAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public JpaAuthor add(String firsname, String lastname) throws Exception {
        JpaAuthor author = new JpaAuthor(firsname, lastname);
        try {
            authorRepository.save(author);
        }
        catch(DataIntegrityViolationException exception) {
            handlerException(exception, author.toString()); //Todo вот такая обработка ошибки....
        }
        return author;
    }

    @Override
    public long getCount() {
        return authorRepository.count();
    }

    @Override
    public List<JpaAuthor> getAll(int page, int amountByOnePage) {
        return authorRepository.findAll(PageRequest.of(page,amountByOnePage)).getContent();
    }

    @Override
    public JpaAuthor findById(long id) {
        return authorRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, JpaAuthor.class.getSimpleName(), id)));
    }

    @Override
    public List<JpaAuthor> find(String frstname, String lastname) throws Exception {
        List<JpaAuthor> authors = null;
        try {
            authors = Lists.newArrayList(authorRepository.findByFirstnameOrLastname(frstname, lastname));
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, JpaAuthor.class.getSimpleName());
        }
        return authors;
    }

    @Override
    public List<JpaBook> getBooks(long id){
        return Lists.newArrayList(authorRepository.getBooks(id));
    }

    @Override
    public JpaAuthor update(long id, String firstname, String lastname) throws Exception {
        JpaAuthor author = authorRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, JpaAuthor.class.getSimpleName(), id)));
        if(firstname!= null)
            author.setFirstname(firstname);
        if(lastname!=null)
            author.setLastname(lastname);
        try {
            authorRepository.save(author);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, JpaAuthor.class.getSimpleName());
        }
        return author;
    }

    @Override
    public void delete(long id, boolean withBook) throws Exception {
        if(getBooks(id).isEmpty() && !withBook)
            throw new IllegalStateException(String.format(ASSOCIATED_ERROR_STRING, JpaAuthor.class.getSimpleName(), JpaBook.class.getSimpleName()));
        else
            deleteWithBook(id);
    }

    @Override
    public void delete(long id) throws Exception {
        if(!getBooks(id).isEmpty())
            throw new IllegalStateException(String.format(ASSOCIATED_ERROR_STRING, JpaBook.class.getSimpleName(), JpaBook.class.getSimpleName()));
        else
            deleteWithBook(id);
    }

    private void deleteWithBook(long id) throws Exception {
        try {
            authorRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, JpaAuthor.class.getSimpleName());
        }
    }

    @Override
    public void deleteAll() {
        authorRepository.deleteAll();
    }
}