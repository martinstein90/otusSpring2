package com.martin.service;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.repository.AuthorRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorJpaService implements AuthorService{

    private final AuthorRepository authorRepository;

    private final String ERROR_STRING = "Операция с объектом %s не выполнена";
    private final String DUPLICATE_ERROR_STRING = "Запись %s существует";
    private final String EMPTY_RESULT_BY_ID_ERROR_STRING = "Объект %s c id %d не найден";

    public AuthorJpaService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void add(String firsname, String lastname) throws Exception {
        Author author = new Author(firsname, lastname);
        try {
            authorRepository.insert(author);
        }
        catch (DuplicateKeyException exception){
            throw new Exception(String.format(DUPLICATE_ERROR_STRING, author));
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, author));
        }
    }

    @Override
    public long getCount() {
        return authorRepository.getCount();
    }

    @Override
    public List<Author> getAll(int page, int amountByOnePage) {
        return authorRepository.getAll(page, amountByOnePage);
    }

    @Override
    public Author findById(long id) throws Exception {
        Author author;
        try {
            author = authorRepository.findById(id);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Author.class.getSimpleName(), id));
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
        return author;
    }

    @Override
    public List<Author> find(String frstname, String lastname) throws Exception {
        Author author = new Author(frstname, lastname);
        List<Author> authors;
        try {
            authors = authorRepository.find(author);
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
        return authors;
    }

    @Override
    public List<Book> getBooks(long id){
        return authorRepository.getBooks(id);
    }

    @Override
    public void update(long id, String firstname, String lastname) throws Exception {
        Author author = new Author(firstname, lastname);
        try {
            authorRepository.update(id, author);
        }
        catch(DataAccessException exception) {
            System.out.println(exception.getMessage());
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
   }

    @Override
    public void delete(long id) throws Exception {
        try {
            authorRepository.delete(id);
        }
        catch(DataAccessException exception) {
            System.out.println(exception.getMessage());
            throw new Exception(String.format(ERROR_STRING, Author.class.getSimpleName()));
        }
    }
}
