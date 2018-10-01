package com.martin.servlet;

import com.martin.domain.Author;
import com.martin.service.AuthorService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity> save(@PathVariable("id") String id, AuthorDto authorDto) throws Exception {
        System.out.println("save");
        Author authorDao = AuthorDto.toDomainObject(authorDto);
        return authorService.update(new ObjectId(id), authorDao.getFirstname(), authorDao.getLastname())
                .map(AuthorDto::toDataTransferObject)
                .map(dto->ResponseEntity.status(HttpStatus.ACCEPTED).body(dto));
    }

    @PostMapping("/authors/insert")
    public Mono<ResponseEntity> insert(AuthorDto authorDto) throws Exception {
        System.out.println("insert");
        Author authorDao = AuthorDto.toDomainObject(authorDto);
        return authorService.add(authorDao.getFirstname(), authorDao.getLastname())
                .map(AuthorDto::toDataTransferObject)
                .map(dto->ResponseEntity.status(HttpStatus.CREATED).body(dto));
    }

    @ExceptionHandler(Throwable.class)
    public Mono<ResponseEntity> handleException(Throwable ex) {
        System.out.println("handleException " + ex.getMessage());
        return Mono.just(new ErrorDto(ex.getMessage()))
                .map(dto->ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto));
    }
}
