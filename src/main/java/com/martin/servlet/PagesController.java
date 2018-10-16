package com.martin.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PagesController {


    // UsernamePasswordAuthenticationToken

    @GetMapping("/")
    public String indexPage(Model model) {
        System.out.println("indexPage");

        print();
//
//        model.addAttribute("isAuthenticated", SecurityContextHolder
//                .getContext().getAuthentication().isAuthenticated());
        return "index";
    }

    @GetMapping("/public")
    public String publicPage() {
        System.out.println("publicPage");
        print();


        return "public";
    }

    @GetMapping("/authenticated")
    public String authenticatedPage() {
        System.out.println("authenticatedPage");
        print();
        return "authenticated";
    }

    @GetMapping("/login")
    public String loginPage() {
        System.out.println("loginPage");
        print();
        return "login";
    }

    @PostMapping("/success")
    public String successPage() {
        System.out.println("successPage");
        print();
        return "success";
    }

    @GetMapping("/error")
    public String errorPage() {
        System.out.println("errorPage");
        return "error";
    }
//
//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleException(Exception ex) {
//        System.out.println("handleException " + ex.getMessage());
//
//        ModelAndView modelAndView = new ModelAndView("exception");
//        modelAndView.addObject("error", ex.getMessage());
//        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
//        return modelAndView;
//    }

    private void print() {
        System.out.println(SecurityContextHolder
                .getContext().getAuthentication());
    }
}
