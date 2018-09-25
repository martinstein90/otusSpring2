package com.martin.service;

import com.martin.domain.Author;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorService {

    Mono<Author> add(String firsname, String lastname) throws Exception;
    Mono<Long> getCount();
    Flux<Author> getAll(int page, int amountByOnePage);
    Mono<Author> findById(ObjectId id) throws Exception;
    Flux<Author> find(String firstname, String lastname) throws Exception;
    Mono<Author> update(ObjectId id, String firstname, String lastname) throws Exception;
    Mono<Void> delete(ObjectId id) throws Exception;
    Mono<Void> deleteAll();
}
