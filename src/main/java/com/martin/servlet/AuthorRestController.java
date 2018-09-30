package com.martin.servlet;

import com.martin.domain.Author;
import com.martin.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthorRestController {

    @Autowired
    private final AuthorService authorService;

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public AuthorDto getAll() {
        System.out.println("getAll");
        Flux<AuthorDto> authors = authorService.getAll(0, Integer.MAX_VALUE).map(AuthorDto::toDataTransferObject);
        return authors;
    }

    @PostMapping("/authors/save/{id}")
    public AuthorDto save(@PathVariable("id") String id, AuthorDto authorDto) throws Exception {
        System.out.println("save");
        Author authorDao = AuthorDto.toDomainObject(authorDto);
        return authorService.update(new ObjectId(id), authorDao.getFirstname(), authorDao.getLastname())
                .map(AuthorDto::toDataTransferObject);

    }

    @PostMapping("/authors/insert")
    public AuthorDto insert(AuthorDto authorDto) throws Exception {
        System.out.println("insert");
        Author authorDao = AuthorDto.toDomainObject(authorDto);
        return authorService.add(authorDao.getFirstname(), authorDao.getLastname())
                .map(AuthorDto::toDataTransferObject);
    }

    @ExceptionHandler(Exception.class) //Todo тест на обработчик ошибок валится. Симптомы такие же как когда использовались @RequestBody или HttpServletResponse
    public ErrorDto handleException(Exception ex) {
        System.out.println("handleException " + ex.getMessage());
        return Mono.just(new ErrorDto(ex.getMessage()));
    }
}
