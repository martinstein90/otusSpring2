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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Locale;

import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorController.class)
@ContextConfiguration
public class AuthorControllerTest {

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
        this.mvc.perform(post("/authors/insert")
                            .param("firstname", "Alex")
                            .param("lastname", "Pushkin"))
                                .andExpect(status().is(SC_CREATED))
                                .andExpect(content().string(containsString("Alex Pushkin добавлен")));
    }

    @Test
    public void saveTest() throws Exception {
        this.mvc.perform(post("/authors/save")
                .param("id", "5b85490ebce9cb1908cb809b")
                .param("firstname", "Alex")
                .param("lastname", "Pushkin"))
                .andExpect(status().is(SC_ACCEPTED))
                .andExpect(content().string(containsString("Alex Pushkin обновлен")));
    }

    @Test
    public void getErrorHtml() throws Exception {
        this.mvc.perform(post("/authors/insert")
                .param("firstname", "Fedor")
                .param("lastname", "Dostoevsky"))
                .andExpect(status().is(SC_BAD_REQUEST))
                .andExpect(content().string(containsString("Запись Fedor Dostoevsky существует")));
    }


    @Test
    public void localizationTest() throws Exception {
        this.mvc.perform(get("/").locale(Locale.US))
                .andExpect(content().string(containsString("My super online library")));
    }

}