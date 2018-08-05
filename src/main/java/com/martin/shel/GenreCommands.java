package com.martin.shel;

import com.martin.domain.Book;
import com.martin.domain.Genre;
import com.martin.service.GenreService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

import static com.martin.helper.Ansi.ANSI_RED;
import static com.martin.helper.Ansi.ANSI_RESET;

@ShellComponent
public class GenreCommands {
    private final GenreService service;

    private final String WORKING_COLOR = ANSI_RED;
    private final String RESET_COLOR = ANSI_RESET;

    public GenreCommands(GenreService service) {
        this.service = service;
    }

    @ShellMethod("add genre")
    public void addGenre(@ShellOption String title) {
        try {
            service.add(title);
            System.out.println(WORKING_COLOR + "Жанр добавлен" + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("get genres count")
    public void getGenresCount() {
        try {
            System.out.println(WORKING_COLOR + "Кол-во жанров:" + service.getCount() + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("get all genres")
    public void getAllGenres( @ShellOption int amountByOnePage) {
        long genresCount = service.getCount();
        int pages = (int)Math.ceil((double)genresCount/amountByOnePage);
        for(int page = 1; page<=pages; page++) {
            List<Genre> allGenres = service.getAll(page, amountByOnePage);
            System.out.println(WORKING_COLOR + "Page: " + page + RESET_COLOR);
            System.out.println(WORKING_COLOR + allGenres + RESET_COLOR);
        }
    }

    @ShellMethod("find genre")
    public void findGenre(@ShellOption String title) {
        try {
            List<Genre> genres = service.find(title);
            System.out.println(WORKING_COLOR + genres + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("find genre by id")
    public void findgGenreById(@ShellOption int id) {
        try {
            Genre genre = service.findById(id);
            System.out.println(WORKING_COLOR + genre + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("find book by genre id")
    public void getBookByGenreId(@ShellOption int id) {
        try {
            List<Book> books = service.getBooks(id);
            System.out.println(WORKING_COLOR + books + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }


    @ShellMethod("update genre")
    public void updateGenreById(@ShellOption int id, @ShellOption String title) {
        try {
            service.update(id, title);
            System.out.println(WORKING_COLOR + "Жанр обновлен" + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("delete genre")
    public void deleteGenreById(@ShellOption int id) {
        try {
            service.delete(id);
            System.out.println(WORKING_COLOR + "Жанр удален" + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }
}
