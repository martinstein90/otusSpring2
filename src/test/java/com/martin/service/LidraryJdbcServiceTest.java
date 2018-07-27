package com.martin.service;

import com.martin.dao.LibraryDao;
import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.IntHolder;
import org.omg.CORBA.ObjectHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LidraryJdbcServiceTest {

    @MockBean
    private LibraryDao dao;

    private static Set<Author> authors = new HashSet<>();
    private static int authorCount;

    private static Set<Genre> genres = new HashSet<>();
    private static int genreCount;

    private static Set<Book> books = new HashSet<>();
    private static int bookCount;

    @Autowired
    LidraryJdbcService service;

    @Before
    public void setUp() {
        initLibraryDaoMockForInsert();
        initLibraryDaoMockForFindById();
        clear();
    }

    private void initLibraryDaoMockForInsert() {
        when(dao.insert(any(Author.class))).
                thenAnswer(answer -> {
                    Author author = (Author) (answer.getArguments())[0];
                    if (isAuthorsContains(author))
                        throw new DuplicateKeyException("DuplicateKeyException");
                    author.setId(++authorCount);
                    authors.add(author);
                    return 1;
                });

        when(dao.insert(any(Genre.class))).
                thenAnswer(answer -> {
                    Genre genre = (Genre) (answer.getArguments())[0];
                    if (isGenresContains(genre))
                        throw new DuplicateKeyException("DuplicateKeyException");
                    genre.setId(++genreCount);
                    genres.add(genre);
                    return 1;
                });

        when(dao.insert(any(Book.class))).
                thenAnswer(answer -> {
                    Book book = (Book) (answer.getArguments())[0];
                    if (isBooksContains(book))
                        throw new DuplicateKeyException("DuplicateKeyException");
                    book.setId(++genreCount);
                    books.add(book);
                    return 1;
                });
    }

    private void initLibraryDaoMockForFindById() {
        when(dao.findById(any(Class.class), any(Integer.class))).
                thenAnswer(answer -> {
                    Class cl = (Class)(answer.getArguments())[0];
                    int id = (int)(answer.getArguments())[1];
                    Author authorById = null;
                    if(cl == Author.class) {
                        authorById = findAuthorById(id);
                        if (authorById == null)
                            throw new EmptyResultDataAccessException(0);
                    }
                    if else (cl == Genre.class) {

                    }
                    if else(cl == Book.class) {

                    }
                    return authorById;
                });
    }


    private boolean isAuthorsContains(Author author) {
        BooleanHolder isContains = new BooleanHolder();
        isContains.value = false;

        authors.forEach(action -> {
            if (action.getFirstname().equals(author.getFirstname()) &&
                    action.getLastname().equals(author.getLastname())) {
                isContains.value = true;
                return;
            }
        });
        return isContains.value;
    }
    private boolean isGenresContains(Genre genre) {
        BooleanHolder isContains = new BooleanHolder();
        isContains.value = false;

        genres.forEach(action -> {
            if (action.getTitle().equals(genre.getTitle())) {
                isContains.value = true;
                return;
            }
        });
        return isContains.value;
    }
    private boolean isBooksContains(Book book) {
        BooleanHolder isContains = new BooleanHolder();
        isContains.value = false;

        books.forEach(action -> {
            if (action.getTitle().equals(book.getTitle()) &&
                action.getAuthor().equals(book.getAuthor()) &&
                action.getGenre().equals(book.getGenre())) {
                isContains.value = true;
                return;
            }
        });
        return isContains.value;
    }

    private Author findAuthorById(int id) {
        ObjectHolder foundAuthor = new ObjectHolder();
        authors.forEach(action -> {
            if (action.getId() == id) {
                foundAuthor.value = (org.omg.CORBA.Object) action;
            }
        });
        return (Author) foundAuthor.value;
    }
    private Genre findGenreById(int id) {
        ObjectHolder foundGenre = new ObjectHolder();
        genres.forEach(action -> {
            if (action.getId() == id) {
                foundGenre.value = (org.omg.CORBA.Object) action;
            }
        });
        return (Genre) foundGenre.value;
    }
    private Book findBookById(int id) {
        ObjectHolder foundBook = new ObjectHolder();
        genres.forEach(action -> {
            if (action.getId() == id) {
                foundBook.value = (org.omg.CORBA.Object) action;
            }
        });
        Book book = (Book) foundBook.value;


    }

    private void clear() {
        books.clear();
        genres.clear();
        authors.clear();
    }

    @Test
    public void checkInsertAuthor() throws Exception {
        String firsname = "Ivan";
        String lastname = "Petrov";
        service.addAuthor(firsname, lastname);

        Assert.assertEquals(true, isAuthorsContains(new Author(firsname, lastname)));
    }

    @Test(expected = Exception.class)
    public void getExceptionAfterDoubleInsertAuthor() throws Exception {
        String firsname = "Ivan";
        String lastname = "Petrov";
        service.addAuthor(firsname, lastname);
        service.addAuthor(firsname, lastname);
    }

    @Test
    public void checkInsertGenre() throws Exception {
        String title = "Horror";
        service.addGenre(title);

        Assert.assertEquals(true, isGenresContains(new Genre(title)));
    }

    @Test(expected = Exception.class)
    public void getExceptionAfterDoubleInsertGenre() throws Exception {
        String title = "Horror";
        service.addGenre(title);
        service.addGenre(title);
    }
    @Test
    public void checkInsertBookWithNewAuthorAndGenre() throws Exception {
        String firstname = "Darya";
        String lastname = "Dontsova";
        String genreTitle = "Detective";
        String bookTitle = "One";

        service.addBook(bookTitle, firstname, lastname, genreTitle);

        Assert.assertEquals(true, isAuthorsContains(new Author(firstname, lastname)));
        Assert.assertEquals(true, isGenresContains(new Genre(genreTitle)));
        Assert.assertEquals(true, isBooksContains(new Book(bookTitle, new Author(firstname, lastname), new Genre(genreTitle))));
    }

    @Test(expected = Exception.class)
    public void getExceptionAfterDoubleInsertBook() throws Exception {
        String firstname = "Darya";
        String lastname = "Dontsova";
        String genreTitle = "Detective";
        String bookTitle = "OneOne";
        service.addBook(bookTitle, firstname, lastname, genreTitle);
        service.addBook(bookTitle, firstname, lastname, genreTitle);
    }

    @Test(expected = Exception.class)
    public void getExceptionAfterInsertBookWithExistingAuthor() throws Exception {
        String firstname = "Darya";
        String lastname = "Dontsova";
        String genreTitle = "Detective";
        String bookTitle = "OneOne";

        service.addAuthor(firstname, lastname);
        service.addBook(bookTitle, firstname, lastname, genreTitle);
    }

    @Test(expected = Exception.class)
    public void getExceptionAfteInsertBookWithExistingGenre() throws Exception {
        String firstname = "Darya";
        String lastname = "Dontsova";
        String genreTitle = "Detective";
        String bookTitle = "OneOne";

        service.addGenre(genreTitle);
        service.addBook(bookTitle, firstname, lastname, genreTitle);
    }



    @Test(expected = Exception.class)
    public void getExceptionAfterInsertBookWithIdNotExistingAuthor() throws Exception {
        service.addGenre("Horror");
        service.addGenre("Detective");
        service.addBook("title", 100, 1);
    }
}