package com.martin.servlet;

import com.martin.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthorController {

    @Autowired
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/")
    public String indexPage() {
        System.out.println("indexPage");
        return "index";
    }

    @GetMapping("/public")
    public String publicPage() {
        System.out.println("publicPage");
        return "public";
    }

    @GetMapping("/authenticated")
    public String authenticatedPage() {
        System.out.println("authenticatedPage");
        return "authenticated";
    }

    @GetMapping("/login")
    public String loginPage() {
        System.out.println("loginPage");
        return "login";
    }

    @PostMapping("/success")
    public String successPage() {
        System.out.println("successPage");
        return "success";
    }

    @GetMapping("/error")
    public String errorPage() {
        System.out.println("errorPage");
        return "error";
    }


    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        System.out.println("handleException " + ex.getMessage());

        ModelAndView modelAndView = new ModelAndView("exception");
        modelAndView.addObject("error", ex.getMessage());
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        return modelAndView;
    }
}
