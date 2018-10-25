package com.martin.servlet;

import com.martin.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PagesController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/addAuthor")
    public String addAuthorPage() {
        return "addAuthor";
    }

    @PostMapping("/author")
    public String addAuthorPost(String firstname, String lastname, Model model) {
        authorService.add(firstname, lastname);
        model.addAttribute("message",
                "Автор " + firstname + " " + lastname + " добавлен!");
        return "success";
    }

    @GetMapping("/author")
    public String addAuthorGet(Model model) {
        model.addAttribute("authors", authorService.getAll());
        return "list";
    }

}
