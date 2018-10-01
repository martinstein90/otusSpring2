package com.martin.service;

import com.martin.caching.Cachable;
import com.martin.domain.Author;
import com.martin.repository.AuthorRepository;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.martin.caching.Cachable.Operation.*;
import static com.martin.service.HandlerException.*;

@Service
@Scope(value = "singleton")
public class AuthorRxService implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorRxService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Mono<Author> add(String firstname, String lastname) throws Exception {
        Author author = new Author(firstname, lastname);
        Mono<Author> save = authorRepository.save(author)
                .doOnError(exception->handlerException(exception, author.toString()));
        return save;
    }

    @Override
    public Mono<Long> getCount() {
        return authorRepository.count();
    }

    @Cachable(target = Author.class, operation = ADD, disable = true)
    @Override
    public Flux<Author> getAll(int page, int amountByOnePage) {
        Flux<Author> authors = authorRepository.findAll()
                .skip(page * amountByOnePage)
                .take(amountByOnePage);
        return authors;
    }

    @Cachable(target = Author.class, operation = GET, disable = true)
    @Override
    public Mono<Author> findById(ObjectId id) {
        Mono<Author> byId = authorRepository.findById(id);
        Author author = byId.block();
        if(author == null)
            throw new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Author.class.getSimpleName(), id));
        return byId;
    }

    @Override
    public Flux<Author> find(String firstname, String lastname) throws Exception {
        Flux<Author> authors = authorRepository.findByFirstnameOrLastname(firstname, lastname)
                    .doOnError(exception->handlerException(exception, Author.class.getSimpleName()));
        return authors;
    }

    @Override
    public Mono<Author> update(ObjectId id, String firstname, String lastname) throws Exception {
        Author byId = authorRepository.findById(id).block();
        if(byId == null)
            throw new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Author.class.getSimpleName(), id));
        if(firstname != null)
            byId.setFirstname(firstname);
        if(lastname != null)
            byId.setLastname(lastname);
        return authorRepository.save(byId);
    }

    public Mono<Void> delete(ObjectId id) throws Exception {
        return authorRepository.deleteById(id)
                .doOnError(exception->handlerException(exception, Author.class.getSimpleName()));
    }

    @Override
    public Mono<Void> deleteAll() {
        return authorRepository.deleteAll();
    }
}
