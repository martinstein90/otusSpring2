package com.martin.servlet;

import com.martin.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AuthorController {

    @Autowired
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public @ResponseBody List<AuthorDto> get() {
        System.out.println("List<PersonDto> get");
        List<AuthorDto> collect = authorService.getAll(0, 10).stream()
                .map(AuthorDto::toDataTransferObject)
                .collect(Collectors.toList());
        return collect;
    }


}
