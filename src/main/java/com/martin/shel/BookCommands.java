package com.martin.shel;

import com.martin.domain.Book;
import com.martin.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

import static com.martin.helper.Ansi.ANSI_RED;
import static com.martin.helper.Ansi.ANSI_RESET;

public class BookCommands {

    private final BookService service;

    private final String WORKING_COLOR = ANSI_RED;
    private final String RESET_COLOR = ANSI_RESET;


    @Autowired
    public BookCommands(BookService service) {
        this.service = service;
    }

    @ShellMethod("get books count")
    public void getBooksCount() {
        try {
            System.out.println(WORKING_COLOR + "Кол-во книг:" + service.getCount() + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("get books count")
    public void getAllBooks( @ShellOption int amountByOnePage) {
        long booksCount = service.getCount();
        int pages = (int)Math.ceil((double)booksCount/amountByOnePage);
        for(int page = 1; page<=pages; page++) {
            List<Book> allBooks = service.getAll(page, amountByOnePage);
            System.out.println(WORKING_COLOR + "Page: " + page + RESET_COLOR);
            System.out.println(WORKING_COLOR + allBooks + RESET_COLOR);
        }
    }

    @ShellMethod("find book by title ")
    public void findBookByTitle(@ShellOption String title) {
        try {
            List<Book> books = service.findByTitle(title);
            System.out.println(WORKING_COLOR + books + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("find book by id")
    public void findBbookById(@ShellOption int id) {
        try {
            Book book = service.findById(id);
            System.out.println(WORKING_COLOR + book + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("delete book")
    public void deleteBookById(@ShellOption int id) {
        try {
            service.deleteBook(id);
            System.out.println(WORKING_COLOR + "Книга удалена" + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }
}
