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

@Controller
public class AuthorController {

    @Autowired
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public String getAll(Model model) {
        List<Author> authors = authorService.getAll(0, Integer.MAX_VALUE); //Todo Конечно Integer.MAX_VALUE - некрасиво так делать,
        model.addAttribute("authors", authors);                     // но реализовывать вкладки по 10 авторов времени нет.
        return "list";                                                           //Хотя getAll с pages задумывалась как раз для этого
    }

    @RequestMapping(value = "/authors/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("action", "insert");
        return "edit";
    }

    @RequestMapping(value = "/authors/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value="id") String id,
                       Model model) throws Exception {
        Author finded = authorService.findById(new ObjectId(id));
        model.addAttribute("action", "save");
        model.addAttribute("author", finded);
        return "edit";
    }

    @RequestMapping(value = "/authors/insert", method = RequestMethod.POST)
    public String insert(@RequestParam(value="firstname") String firstname,
                         @RequestParam(value="lastname") String lastname,
                         Model model) throws Exception {
        Author added = authorService.add(firstname, lastname);
        model.addAttribute("author", added);
        model.addAttribute("action", "insert");
        return "edited";
    }

    @RequestMapping(value= "/authors/save", method = RequestMethod.POST)
    public String save(@RequestParam("id") String id,
                       @RequestParam("firstname") String firstname,
                       @RequestParam("lastname") String lastname,
                       Model model) throws Exception {
        Author author = authorService.findById(new ObjectId(id));
        Author updated = authorService.update(author.getId(), firstname, lastname);
        model.addAttribute("author", updated);
        model.addAttribute("action", "save");
        return "edited";
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        System.out.println("handleException " + ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
