package com.martin.servlet;

import com.martin.domain.Author;
import com.martin.security.SecurityService;
import com.martin.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PagesController {

    @Autowired
    SecurityService securityService;
    @Autowired
    private AuthorService authorService;


    @GetMapping("/")
    public String indexPage(Model model) {
        System.out.println("indexPage");

        return "index";
    }



    @GetMapping("/public")
    @Transactional
    public String publicPage() {
        System.out.println("publicPage");
        print();
        authorService.add("Alex", "Pushkin");

        return "public";
    }

    @GetMapping("/authenticated")
    public String authenticatedPage() {
        System.out.println("authenticatedPage");
        print();
        return "authenticated";
    }

    @GetMapping("/test")
    public String testPage() {
        System.out.println("test");
        print();

        securityService.addPermission(0, null);
        return "test";
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal().getClass());
    }
}
