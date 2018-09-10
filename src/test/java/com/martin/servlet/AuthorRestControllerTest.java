package com.martin.servlet;


import com.martin.domain.Author;
import com.martin.service.AuthorService;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorRestController.class)
@ContextConfiguration
public class AuthorRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Before
    public void initMock() throws Exception {
        when(authorService.add(any(String.class), any(String.class))).thenAnswer(invocation-> {
            Object[] args = invocation.getArguments();
            String firstname  = (String)args[0];
            String lastname  = (String)args[1];
            Author author = new Author(firstname, lastname);
            if(firstname.equals("Fedor") && lastname.equals("Dostoevsky"))
                throw new RuntimeException("Запись Fedor Dostoevsky существует");
            return author;
        });

        when(authorService.findById(any(ObjectId.class))).thenReturn(new Author("Sergey", "Esenin"));

        when(authorService.update(any(), any(String.class), any(String.class))).thenAnswer(invocation-> {
            Object[] args = invocation.getArguments();
            String newFirstname = (String) args[1];
            String newLastname = (String) args[2];
            return new Author(newFirstname, newLastname);
        });
    }

    @Test
    public void insertTest() throws Exception {
        String firstname = "Alex";
        String lastname = "Pushkin";
        mvc.perform(post("/authors/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"firstname\" : \"" + firstname + "\" , \"lastname\" : \"" + lastname + "\" }"))
                    .andExpect(status().is(SC_CREATED))
                    .andExpect(jsonPath("$.firstname").value(firstname))
                    .andExpect(jsonPath("$.lastname").value(lastname));
    }

    @Test
    public void saveTest() throws Exception {
        String id = "5b85490ebce9cb1908cb809b";
        String firstname = "Alex";
        String lastname = "Pushkin";
        mvc.perform(post("/authors/save/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"firstname\" : \"" + firstname + "\" , \"lastname\" : \"" + lastname + "\" }"))
                    .andExpect(status().is(SC_ACCEPTED))
                    .andExpect(jsonPath("$.firstname").value(firstname))
                    .andExpect(jsonPath("$.lastname").value(lastname));
    }

    @Test
    public void handleExceptionTest() throws Exception {
        String firstname = "Fedor";
        String lastname = "Dostoevsky";
        this.mvc.perform(post("/authors/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"firstname\" : \"" + firstname + "\" , \"lastname\" : \"" + lastname + "\" }"))
                    .andExpect(status().is(SC_BAD_REQUEST))
                    .andExpect(content().string(containsString("Запись Fedor Dostoevsky существует")));
    }
}