package com.martin.servlet;

import com.martin.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class PagesController {

    @GetMapping("/")
    public String indexPage() {
        log.info("indexPage");
        return "index";
    }

    @GetMapping("/public")
    public String publicPage() {
        log.info("publicPage");
        print();
        return "public";
    }

    @GetMapping("/authenticated")
    public String authenticatedPage() {
        log.info("authenticatedPage");
        print();
        return "authenticated";
    }

    @GetMapping("/login1")
    public String loginPage() {
        log.info("loginPage");
        print();
        return "login1";
    }

    @PostMapping("/success")
    public String successPage(String username, Model model) {
        log.info("successPage");
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
        log.info(SecurityContextHolder.getContext().getAuthentication().toString());
    }
}
