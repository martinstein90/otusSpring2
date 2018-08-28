package com.martin.servlet;

import com.martin.domain.Author;
import com.martin.service.AuthorService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_OK;

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
                         Model model, HttpServletResponse  response) throws Exception {
        Author added = authorService.add(firstname, lastname);
        model.addAttribute("author", added);
        model.addAttribute("action", "insert");
        response.setStatus(SC_CREATED );
        return "edited";
    }

    @RequestMapping(value= "/authors/save", method = RequestMethod.POST)
    public String save(@RequestParam("id") String id,
                       @RequestParam("firstname") String firstname,
                       @RequestParam("lastname") String lastname,
                       Model model, HttpServletResponse  response) throws Exception {
        Author author = authorService.findById(new ObjectId(id));
        Author updated = authorService.update(author.getId(), firstname, lastname);
        model.addAttribute("author", updated);
        model.addAttribute("action", "save");
        response.setStatus(SC_ACCEPTED );
        return "edited";
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("error", ex.getMessage());
        modelAndView.setStatus(HttpStatus.BAD_REQUEST); //Todo как то не очень здесь я использую HttpStatus а выше int из HttpServletResponse
        return modelAndView;
    }

}
