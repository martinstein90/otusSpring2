package com.martin.servlet;

import com.martin.domain.Author;
import com.martin.service.AuthorService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AuthorController {

    @Autowired
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping("/")
    public String start(Model model) {
        System.out.println("start");
        model.addAttribute("name", "Alex");
        return "index";
    }

    @GetMapping("/authors")
    public String getAll(Model model) {
        System.out.println("getAll");
        List<AuthorDto> authors = authorService.getAll(0, 10).stream()
                .map(AuthorDto::toDataTransferObject)
                .collect(Collectors.toList());
        model.addAttribute("authors", authors);
        model.addAttribute("name", "Alex");

        return "list";
    }

    @RequestMapping(value = "/authors/", method = RequestMethod.GET)
    @ResponseBody public AuthorDto create(AuthorDto authorDto) throws Exception {
        Author author = AuthorDto.toDomainObject(authorDto);
        Author authorAdd = authorService.add(author.getFirstname(), author.getLastname());
        return AuthorDto.toDataTransferObject(authorAdd);
    }


    @RequestMapping(value = "/authors/{id}", method = RequestMethod.GET)
    @ResponseBody public AuthorDto get(@PathVariable("id") String id) throws Exception {
        Author author = authorService.findById(new ObjectId(id));
        System.out.println("author = " + author);
        return AuthorDto.toDataTransferObject(author);
    }


    @PutMapping("/authors/{id}/holder")
    @ResponseBody public void change(@PathVariable("id") String id, @RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname) throws Exception {
        Author author = authorService.findById(new ObjectId(id));
        authorService.update(author.getId(), firstname, lastname);
    }

    @DeleteMapping("/authors/{id}")
    public void delete(@PathVariable("id") String id) throws Exception {
        authorService.delete(new ObjectId(id));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        System.out.println("handleException " + ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
