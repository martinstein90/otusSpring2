package com.martin.servlet;

import com.martin.domain.Author;
import com.martin.service.AuthorService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static javax.servlet.http.HttpServletResponse.*;

@RestController
public class AuthorRestController {

    @Autowired
    private final AuthorService authorService;

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public Flux<AuthorDto> getAll() {
        System.out.println("getAll");
        Flux<AuthorDto> authors = authorService.getAll(0, Integer.MAX_VALUE).map(AuthorDto::toDataTransferObject);
        return authors;
    }

    @PostMapping("/authors/save/{id}")
    public Mono<AuthorDto> save(@PathVariable("id") String id, AuthorDto authorDto) throws Exception {
        System.out.println("save");
        Author authorDao = AuthorDto.toDomainObject(authorDto);
        return authorService.update(new ObjectId(id), authorDao.getFirstname(), authorDao.getLastname())
                .map(AuthorDto::toDataTransferObject);

    }

    @PostMapping("/authors/insert")
    //Todo Почему тесты валятся если использовать @RequestBody или HttpServletResponse. Как тогда перелать код 201, 202 ?
    public Mono<AuthorDto> insert(AuthorDto authorDto) throws Exception {
        System.out.println("insert");
        Author authorDao = AuthorDto.toDomainObject(authorDto);
        return authorService.add(authorDao.getFirstname(), authorDao.getLastname())
                .map(AuthorDto::toDataTransferObject);
    }

    @ExceptionHandler(Exception.class) //Todo тест на обработчик ошибок валится. Симптомы такие же как когда использовались @RequestBody или HttpServletResponse
    public Mono<ErrorDto> handleException(Exception ex) {
        System.out.println("handleException " + ex.getMessage());
        return Mono.just(new ErrorDto(ex.getMessage()));
    }
}
