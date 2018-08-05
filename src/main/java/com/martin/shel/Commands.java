package com.martin.shel;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

import static com.martin.helper.Ansi.ANSI_RED;
import static com.martin.helper.Ansi.ANSI_RESET;

@ShellComponent
public class Commands {
    /*

    @ShellMethod("add book with existing author and genre")
    public void addbookexistingauthorandgenre(@ShellOption String title, @ShellOption int authorId, @ShellOption int genreId) {
        try {
            service.addBook(title, authorId, genreId);
            System.out.println(WORKING_COLOR + "Книга добавлена" + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("add book with new author and genre")
    public void addbookwithnewauthorandgenre(   @ShellOption String authorTitle,
                                                @ShellOption String authorFirsname,
                                                @ShellOption String authorLastname,
                                                @ShellOption String genreTitle) {
        try {
            service.addBook(authorTitle, authorFirsname, authorLastname, genreTitle);
            System.out.println(WORKING_COLOR + "Книга, автор, жанр добавлены" + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }


    @ShellMethod("update book")
    public void updatetitlebookbyid(@ShellOption int id, @ShellOption String title) {
        try {
            service.updateBook(id, title);
            System.out.println(WORKING_COLOR + "Назание книги обновлено" + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("update book")
    public void updatebookbyid(@ShellOption int id,
                               @ShellOption String bookTitle,
                               @ShellOption int authorId,
                               @ShellOption int genreId) {
        try {
            service.updateBook(id, bookTitle,authorId, genreId );
            System.out.println(WORKING_COLOR + "Назание книги обновлено" + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }
*/
}
