package com.martin.shel;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

import static com.martin.helper.Ansi.ANSI_RED;
import static com.martin.helper.Ansi.ANSI_RESET;

@ShellComponent
public class AuthorCommands {

    private final AuthorService service;

    private final String WORKING_COLOR = ANSI_RED;
    private final String RESET_COLOR = ANSI_RESET;

    @Autowired
    public AuthorCommands(AuthorService service) {
        this.service = service;
    }

    @ShellMethod("add author")
    public void addAuthor(@ShellOption String firsname, @ShellOption String lastname) { //todo Почему не работает с addAuthor? С большой буквы автор
        try {
            service.add(firsname, lastname);
            System.out.println(WORKING_COLOR + "Автор добавлен" + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("get author count")
    public void getAuthorCount() {
        try {
            System.out.println(WORKING_COLOR + "Кол-во авторов:" + service.getCount() + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("get all authors")
    public void getAllAuthors(@ShellOption int amountByOnePage) {
        long authorsCount = service.getCount();
        int pages = (int) Math.ceil((double) authorsCount / amountByOnePage);
        for (int page = 1; page <= pages; page++) {
            List<Author> allAuthors = service.getAll(page, amountByOnePage);
            System.out.println(WORKING_COLOR + "Page: " + page + RESET_COLOR);
            System.out.println(WORKING_COLOR + allAuthors + RESET_COLOR);
        }
    }

    @ShellMethod("find author")
    public void findauthor(@ShellOption(defaultValue = "") String firstname, @ShellOption(defaultValue = "") String lastname) {
        if(firstname.isEmpty()) firstname = null;
        if(lastname.isEmpty()) lastname = null;
        try {
            List<Author> authors = service.find(firstname, lastname);
            System.out.println(WORKING_COLOR + authors + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("get books")
    public void getBookByAuthorId(@ShellOption int id) {
        try {
            List<Book> books = service.getBooks(id);
            System.out.println(WORKING_COLOR + books + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("update author")
    public void updateAuthor(@ShellOption int id,
                             @ShellOption String firstname,
                             @ShellOption String lastname) {
        try {
            service.update(id, firstname, lastname);
            System.out.println(WORKING_COLOR + "Автор обновлен" + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("delete author")
    public void deleteAuthor(@ShellOption int id) {
        try {
            service.delete(id);
            System.out.println(WORKING_COLOR + "Автор удален" + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

}