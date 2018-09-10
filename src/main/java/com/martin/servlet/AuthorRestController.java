package com.martin.servlet;

import com.martin.domain.Author;
import com.martin.service.AuthorService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<AuthorDto> getAll(HttpServletResponse response) {
        System.out.println("getAll");
        List<AuthorDto> authors = authorService.getAll(0, Integer.MAX_VALUE).stream()
                .map(AuthorDto::toDataTransferObject)
                .collect(Collectors.toList());
        response.setStatus(SC_OK);
        response.setContentType("application/json");
        return authors;
    }

    @PostMapping("/authors/save/{id}")
    public AuthorDto save(  @PathVariable("id") String id,
                            @RequestBody AuthorDto authorDto,
                            HttpServletResponse response) throws Exception {
        System.out.println("save");
        Author authorDao = AuthorDto.toDomainObject(authorDto);
        Author updated = authorService.update(new ObjectId(id), authorDao.getFirstname(), authorDao.getLastname());
        response.setStatus(SC_ACCEPTED );
        return AuthorDto.toDataTransferObject(updated);
    }

    @PostMapping("/authors/insert")
    public AuthorDto insert(@RequestBody AuthorDto authorDto,
                            HttpServletResponse  response) throws Exception {
        System.out.println("insert");
        Author authorDao = AuthorDto.toDomainObject(authorDto);
        Author inserted = authorService.add(authorDao.getFirstname(), authorDao.getLastname());
        response.setStatus(SC_CREATED);
        return AuthorDto.toDataTransferObject(inserted);
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, HttpServletResponse  response) {
        response.setStatus(SC_BAD_REQUEST);
        return ex.getMessage();
    }
}
