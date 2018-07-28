package com.martin.shel;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import com.martin.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

import static com.martin.helper.Ansi.ANSI_GREEN;
import static com.martin.helper.Ansi.ANSI_RED;
import static com.martin.helper.Ansi.ANSI_RESET;

@ShellComponent
public class Commands {
    private final LibraryService service;

    private final String WORKING_COLOR = ANSI_RED;
    private final String RESET_COLOR = ANSI_RESET;


    @Autowired
    public Commands(LibraryService service) {
        this.service = service;
    }

    @ShellMethod("add author")
    public void addauthor(@ShellOption String firsname, @ShellOption String lastname) { //todo Почему не работает с addAuthor? С большой буквы автор
        try {
            service.addAuthor(firsname, lastname);
            System.out.println(WORKING_COLOR + "Автор добавлен" + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("add genre")
    public void addgenre(@ShellOption String title) {
        try {
            service.addGenre(title);
            System.out.println(WORKING_COLOR + "Жанр добавлен" + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

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

    @ShellMethod("get author count")
    public void getauthorcount() {
        try {
            System.out.println(WORKING_COLOR + "Кол-во авторов:" + service.getAuthorsCount() + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("get genres count")
    public void getgenrescount() {
        try {
            System.out.println(WORKING_COLOR + "Кол-во жанров:" + service.getGenresCount() + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("get books count")
    public void getbookscount() {
        try {
            System.out.println(WORKING_COLOR + "Кол-во книг:" + service.getBooksCount() + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("get books count")
    public void getallauthors( @ShellOption int amountByOnePage) {
        int authorsCount = service.getAuthorsCount();
        int pages = (int)Math.ceil((double)authorsCount/amountByOnePage);
        for(int page = 1; page<=pages; page++) {
            List<Author> allAuthors = service.getAllAuthors(page, amountByOnePage);
            System.out.println(WORKING_COLOR + "Page: " + page + RESET_COLOR);
            System.out.println(WORKING_COLOR + allAuthors + RESET_COLOR);
        }
    }

    @ShellMethod("get genres count")
    public void getallgenres( @ShellOption int amountByOnePage) {
        int genresCount = service.getGenresCount();
        int pages = (int)Math.ceil((double)genresCount/amountByOnePage);
        for(int page = 1; page<=pages; page++) {
            List<Genre> allGenres = service.getAllGenres(page, amountByOnePage);
            System.out.println(WORKING_COLOR + "Page: " + page + RESET_COLOR);
            System.out.println(WORKING_COLOR + allGenres + RESET_COLOR);
        }
    }

    @ShellMethod("get books count")
    public void getallbooks( @ShellOption int amountByOnePage) {
        int booksCount = service.getBooksCount();
        int pages = (int)Math.ceil((double)booksCount/amountByOnePage);
        for(int page = 1; page<=pages; page++) {
            List<Book> allBooks = service.getAllBooks(page, amountByOnePage);
            System.out.println(WORKING_COLOR + "Page: " + page + RESET_COLOR);
            System.out.println(WORKING_COLOR + allBooks + RESET_COLOR);
        }
    }


    @ShellMethod("find author")
    public void findauthor(@ShellOption(defaultValue = "") String firstname, @ShellOption(defaultValue = "") String lastname) {
        if(firstname.isEmpty()) firstname = null;
        if(lastname.isEmpty()) lastname = null;
        try {
            List<Author> authors = service.findAuthor(firstname, lastname);
            System.out.println(WORKING_COLOR + authors + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("find genre")
    public void findgenre(@ShellOption String title) {
        try {
            List<Genre> genres = service.findGenre(title);
            System.out.println(WORKING_COLOR + genres + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("find book by title ")
    public void findbookbytitle(@ShellOption String title) {
        try {
            List<Book> books = service.findBookByTitle(title);
            System.out.println(WORKING_COLOR + books + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("find book by author id")
    public void findbookbyauthorid(@ShellOption int authorId) {
        try {
            List<Book> books = service.findBooksByAuthor(authorId);
            System.out.println(WORKING_COLOR + books + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("find book by genre id")
    public void findbookbygenreid(@ShellOption int genreId) {
        try {
            List<Book> books = service.findBooksByGenre(genreId);
            System.out.println(WORKING_COLOR + books + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("find author by id")
    public void findauthorbyid(@ShellOption int id) {
        try {
            Author author = service.findAuthorById(id);
            System.out.println(WORKING_COLOR + author + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("find genre by id")
    public void findgenrebyid(@ShellOption int id) {
        try {
            Genre genre = service.findGenreById(id);
            System.out.println(WORKING_COLOR + genre + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("find book by id")
    public void findbookbyid(@ShellOption int id) {
        try {
            Book book = service.findBookById(id);
            System.out.println(WORKING_COLOR + book + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }


    @ShellMethod("update author")
    public void updateauthor(@ShellOption int id,
                             @ShellOption String authorFirstname,
                             @ShellOption String authotLastname) {
        try {
            service.updateAuthor(id, authorFirstname, authotLastname);
            System.out.println(WORKING_COLOR + "Автор обновлен" + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("update genre")
    public void updategenrebyid(@ShellOption int id, @ShellOption String genreTitle) {
        try {
            service.updateGenre(id, genreTitle);
            System.out.println(WORKING_COLOR + "Жанр обновлен" + RESET_COLOR);
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

    @ShellMethod("delete author")
    public void deleteauthorbyid(@ShellOption int id) {
        try {
            service.deleteBook(id);
            System.out.println(WORKING_COLOR + "Автор удален" + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("delete genre")
    public void deletegenrebyid(@ShellOption int id) {
        try {
            service.deleteGenre(id);
            System.out.println(WORKING_COLOR + "Жанр удален" + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }

    @ShellMethod("delete book")
    public void deletebookbyid(@ShellOption int id) {
        try {
            service.deleteBook(id);
            System.out.println(WORKING_COLOR + "Книга удалена" + RESET_COLOR);
        } catch (Exception e) {
            System.out.println(WORKING_COLOR + e.getMessage() + RESET_COLOR);
        }
    }
}
