package com.martin.servlet;

import com.martin.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PagesController {

    @GetMapping("/")
    public String indexPage() {
        System.out.println("indexPage");
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

    @GetMapping("/login1")
    public String loginPage() {
        System.out.println("loginPage");
        print();
        return "login1";
    }

    @PostMapping("/success")
    public String successPage(String username, Model model) {
        model.addAttribute("user", username);
        model.addAttribute("username", username);
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
