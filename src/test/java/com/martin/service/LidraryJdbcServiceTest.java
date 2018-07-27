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

import java.util.*;

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
        initLibraryDaoMockForGetCount();
        initLibraryDaoMockForGetAll();
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
                    Object byId = null;
                    if(cl == Author.class) {
                        byId = findAuthorById(id);
                    }
                    else if(cl == Genre.class) {
                        byId = findGenreById(id);
                    }
                    else if(cl == Book.class) {
                        byId = findBookById(id);
                    }

                    if (byId == null)
                        throw new EmptyResultDataAccessException(0);
                    return byId;
                });
    }
    private void initLibraryDaoMockForGetCount() {
        when(dao.getCount(any(Class.class))).
                thenAnswer(answer -> {
                    Class cl = (Class)(answer.getArguments())[0];
                    int size = 0;
                    if(cl == Author.class) {
                        size = authors.size();
                    }
                    else if(cl == Genre.class) {
                        size = genres.size();
                    }
                    else if(cl == Book.class) {
                        size = books.size();
                    }
                    return size;
                });
    }
    private void initLibraryDaoMockForGetAll() {
        when(dao.getAll(any(Class.class), any(Integer.class), any(Integer.class))).
                thenAnswer(answer -> {
                    Class cl = (Class)(answer.getArguments())[0];
                    int page = (int)(answer.getArguments())[1];
                    int amountByOnePage = (int)(answer.getArguments())[2];
                    if(cl == Author.class) {
                        Author[] arrayAuthors = authors.toArray(new Author[authors.size()]);
                        return Arrays.asList(Arrays.copyOfRange(arrayAuthors, page * amountByOnePage, (page + 1) * amountByOnePage));
                    }
                    else if(cl == Genre.class) {
                        Genre[] arrayGenres = genres.toArray(new Genre[genres.size()]);
                        return Arrays.asList(Arrays.copyOfRange(arrayGenres, page * amountByOnePage, (page + 1) * amountByOnePage));

                    }
                    else if(cl == Book.class) {
                        Book[] arrayBooks = books.toArray(new Book[books.size()]);
                        return Arrays.asList(Arrays.copyOfRange(arrayBooks, page * amountByOnePage, (page + 1) * amountByOnePage));
                    }

                    return null;
                });
    }
    private void initLibraryDaoMockForDelete() {
        when(dao.delete(any(Class.class), any(Integer.class))).
                thenAnswer(answer -> {
                    Class cl = (Class)(answer.getArguments())[0];
                    int id = (int)(answer.getArguments())[1];
                    if(cl == Author.class) {
                        if(authors.remove(findAuthorById(id)))
                            throw new Exception();
                    }
                    else if(cl == Genre.class) {
                        if(genres.remove(findGenreById(id)))
                            throw new Exception();
                    }
                    else if(cl == Book.class) {
                        if(books.remove(findBookById(id)))
                            throw new Exception();
                    }
                    return 1;
                });
    }
    private void initLibraryDaoMockForUpdate() {
        when(dao.update(any(Integer.class), any(Author.class))).
                thenAnswer(answer -> {
                    int id = (int)(answer.getArguments())[0];
                    Author newAuthor = (Author) (answer.getArguments())[1];
                    authors.remove(findAuthorById(id));
                    newAuthor.setId(++authorCount);
                    authors.add(newAuthor);
                    return 1;
                });

        when(dao.update(any(Integer.class), any(Genre.class))).
                thenAnswer(answer -> {
                    int id = (int)(answer.getArguments())[0];
                    Genre newGenre = (Genre) (answer.getArguments())[1];
                    genres.remove(findGenreById(id));
                    newGenre.setId(++authorCount);
                    genres.add(newGenre);
                    return 1;
                });

        when(dao.update(any(Integer.class), any(Book.class))).
                thenAnswer(answer -> {
                    int id = (int)(answer.getArguments())[0];
                    Book newBook = (Book) (answer.getArguments())[1];
                    books.remove(findBookById(id));
                    newBook.setId(++bookCount);
                    books.add(newBook);
                    return 1;
                });
    }
    private void initLibraryDaoMockForFind() {
        when(dao.find(any(Author.class))).
                thenAnswer(answer -> {
                    Author criterionAuthor = (Author) (answer.getArguments())[0];
                    List<Author> list = new ArrayList<>();
                    if(criterionAuthor.getFirstname() == null && criterionAuthor.getLastname() == null)
                        throw new IllegalArgumentException();

                    authors.forEach(action -> {
                        boolean res = true;
                        if(criterionAuthor.getFirstname() != null) {
                            if(!action.getFirstname().equals(criterionAuthor.getFirstname()))
                                res = false;
                        }
                        if(criterionAuthor.getLastname() != null) {
                            if(!action.getFirstname().equals(criterionAuthor.getFirstname()))
                                res = false;
                        }
                        if(res)
                            list.add(action);
                    });
                    return list;
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
                action.getAuthor().getFirstname().equals(book.getAuthor().getFirstname()) &&
                action.getAuthor().getLastname().equals(book.getAuthor().getLastname()) &&
                action.getGenre().getTitle().equals(book.getGenre().getTitle())) {
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
        return  (Book) foundBook.value;
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