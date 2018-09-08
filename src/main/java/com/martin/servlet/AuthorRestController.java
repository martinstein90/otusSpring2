package com.martin.servlet;


import com.martin.domain.Author;
import com.martin.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@RestController
public class AuthorRestController {

    @Autowired
    private final AuthorService authorService;

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public List<AuthorDto> getAll(HttpServletResponse response) {
        System.out.println("getAll");
        List<AuthorDto> authors = authorService.getAll(0, Integer.MAX_VALUE).stream()
                .map(AuthorDto::toDataTransferObject)
                .collect(Collectors.toList());
        response.setStatus(SC_OK);
        response.setContentType("application/json");
        return authors;
    }

    @PostMapping("/authors/save")
    public AuthorDto save(  @RequestBody AuthorDto authorDto,
                            HttpServletResponse  response) throws Exception {
        System.out.println("save");
        System.out.println(authorDto);
        Author authorDao = AuthorDto.toDomainObject(authorDto);
        Author author = authorService.findById(authorDao.getId());
        System.out.println("author = " + author);
        Author updated = authorService.update(author.getId(), authorDao.getFirstname(), authorDao.getLastname());
        System.out.println("updated = " + updated);
        response.setStatus(SC_ACCEPTED );
        return AuthorDto.toDataTransferObject(updated);
    }

    @PostMapping("/authors/insert")
    public AuthorDto insert(@RequestBody AuthorDto authorDto,
                            HttpServletResponse  response) throws Exception {
        System.out.println("insert");
        System.out.println(authorDto);
        Author authorDao = AuthorDto.toDomainObject(authorDto);

        Author inserted = authorService.add(authorDao.getFirstname(), authorDao.getLastname());

        response.setStatus(SC_CREATED);
        return AuthorDto.toDataTransferObject(inserted);
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        return ex.getMessage(); //Todo Логика из JavaScript (error : function(e) {addToLog(e.responseText);}) работает. Но ответ приходит с Status Code: 200 Это норм?
    }
}
