package com.martin.servlet;


import com.martin.domain.Author;
import com.martin.service.AuthorService;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.martin.servlet.AuthorDto.toDataTransferObject;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebFluxTest
public class AuthorRestControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private AuthorService authorService;

    private Author esenin = new Author("Sergey", "Esenin");
    private Author chekhov = new Author("Anton", "Chekhov");
    private Author pushkin = new Author("Alex", "Pushkin");
    private Author dostoevsky = new Author("Fedor", "Dostoevsky");
    private final String ERROR = "Запись уже существует";

    @Before
    public void initMock() throws Exception {
        when(authorService.getAll(any(Integer.class), any(Integer.class)))
                .thenAnswer((answer)->{
                    System.out.println("11ssa");return Flux.just(esenin, chekhov);});

        when(authorService.add(any(String.class), any(String.class))).thenAnswer(invocation-> {
            Object[] args = invocation.getArguments();
            String firstname  = (String)args[0];
            String lastname  = (String)args[1];
            if(firstname.equals("Fedor") && lastname.equals("Dostoevsky"))
                throw new RuntimeException(ERROR);
            return Mono.just(new Author(firstname, lastname));
        });

        when(authorService.findById(any(ObjectId.class))).thenAnswer(invocation->
             Mono.just(new Author("Sergey", "Esenin")));

        when(authorService.update(any(), any(String.class), any(String.class))).thenAnswer(invocation-> {
            Object[] args = invocation.getArguments();
            String newFirstname = (String) args[1];
            String newLastname = (String) args[2];
            return Mono.just(new Author(newFirstname, newLastname));
        });

        when(authorService.deleteAll()).thenReturn(Mono.when());
    }

    @Test
    public void getAllTest() {
        webClient.get().uri("/authors")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                    .expectStatus().isOk()
                    .expectBodyList(AuthorDto.class).contains(toDataTransferObject(esenin), toDataTransferObject(chekhov));
    }

    @Test
    public void insertTest() {
        AuthorDto pushkinDto = toDataTransferObject(pushkin);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>(2);
        formData.add("firstname", pushkinDto.getFirstname());
        formData.add("lastname", pushkinDto.getLastname());

        webClient.post().uri("/authors/insert")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(formData))
                .exchange()
                    .expectStatus().isCreated()
                    .expectBody(AuthorDto.class).isEqualTo(pushkinDto);
    }

    @Test
    public void saveTest()  {
        String id = "5b85490ebce9cb1908cb809b";
        AuthorDto pushkinDto = toDataTransferObject(pushkin);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>(2);
        formData.add("firstname", pushkinDto.getFirstname());
        formData.add("lastname", pushkinDto.getLastname());

        webClient.post().uri("/authors/save/" + id)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(formData))
                .exchange()
                    .expectStatus().isAccepted()
                    .expectBody(AuthorDto.class).isEqualTo(pushkinDto);
    }

    @Test
    public void handleExceptionTest() {
        AuthorDto dostoevskyDto = toDataTransferObject(dostoevsky);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>(2);
        formData.add("firstname", dostoevskyDto.getFirstname());
        formData.add("lastname", dostoevskyDto.getLastname());

        webClient.post().uri("/authors/insert")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(formData))
                .exchange()
                .expectStatus().isBadRequest()
                    .expectBody(ErrorDto.class).isEqualTo(new ErrorDto(ERROR));

    }
}