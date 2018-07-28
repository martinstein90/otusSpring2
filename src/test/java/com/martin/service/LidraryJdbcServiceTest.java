package com.martin.service;

import com.martin.dao.LibraryDao;
import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import com.martin.domain.Storable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.ObjectHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
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
        initLibraryDaoMockForFind();
        initLibraryDaoMockForUpdate();
        initLibraryDaoMockForDelete();
        clear();
    }

    private void initLibraryDaoMockForInsert() {
        when(dao.insert(any(Storable.class))).
                thenAnswer(answer -> {
                    Object object = (answer.getArguments())[0];
                    if(object instanceof Author) {
                        insertAuthor((Author)object);
                    }
                    else if(object instanceof Genre) {
                        insertGenre((Genre)object);
                    }
                    else if(object instanceof Book) {
                        insertBook((Book)object);
                    }
                    else
                        throw new IllegalArgumentException("Must be object instanceof Author or Genre or Book");

                    return 1;
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
    private void insertAuthor(Author author) {
        if (isAuthorsContains(author))
            throw new DuplicateKeyException("DuplicateKeyException");
        author.setId(++authorCount);
        authors.add(author);
    }
    private void insertGenre(Genre genre) {
        if (isGenresContains(genre))
            throw new DuplicateKeyException("DuplicateKeyException");
        genre.setId(++genreCount);
        genres.add(genre);
    }
    private void insertBook(Book book) {
        if (isBooksContains(book))
            throw new DuplicateKeyException("DuplicateKeyException");
        book.setId(++bookCount);
        books.add(book);
    }

    private void initLibraryDaoMockForGetCount() {
        when(dao.getCount(any(Class.class))).
                thenAnswer(answer -> {
                    Class cl = (Class)(answer.getArguments())[0];
                    int size;
                    if(cl == Author.class) {
                        size = getAuthorCount();
                    }
                    else if(cl == Genre.class) {
                        size = getGenreCount();
                    }
                    else if(cl == Book.class) {
                        size = getBookCount();
                    }
                    else
                        throw new IllegalArgumentException("Must be cl is a Author.class or Genre.class or Book.class");
                    return size;
                });
    }
    private int getAuthorCount() {
        return authors.size();
    }
    private int getGenreCount() {
        return genres.size();
    }
    private int getBookCount() {
        return books.size();
    }

    private void initLibraryDaoMockForGetAll() {
        when(dao.getAll(any(Class.class), any(Integer.class), any(Integer.class))).
                thenAnswer(answer -> {
                    Class cl = (Class)(answer.getArguments())[0];
                    int page = (int)(answer.getArguments())[1];
                    int amountByOnePage = (int)(answer.getArguments())[2];
                    if(cl == Author.class) {
                        return getAllAuthors(page, amountByOnePage);
                       }
                    else if(cl == Genre.class) {
                        return getAllGenres(page, amountByOnePage);
                    }
                    else if(cl == Book.class) {
                        return getAllBooks(page, amountByOnePage);
                    }
                    else
                        throw new IllegalArgumentException("Must be cl is a Author.class or Genre.class or Book.class");
                });
    }
    private List<Author> getAllAuthors(int page, int amountByOnePage) {
        Author[] arrayAuthors = authors.toArray(new Author[authors.size()]);
        return Arrays.asList(Arrays.copyOfRange(arrayAuthors, page * amountByOnePage, (page + 1) * amountByOnePage));

    }
    private List<Genre> getAllGenres(int page, int amountByOnePage){
        Genre[] arrayGenres = genres.toArray(new Genre[genres.size()]);
        return Arrays.asList(Arrays.copyOfRange(arrayGenres, page * amountByOnePage, (page + 1) * amountByOnePage));

    }
    private List<Book> getAllBooks(int page, int amountByOnePage){
        Book[] arrayBooks = books.toArray(new Book[books.size()]);
        return Arrays.asList(Arrays.copyOfRange(arrayBooks, page * amountByOnePage, (page + 1) * amountByOnePage));

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
                    else
                        throw new IllegalArgumentException("Must be cl is a Author.class or Genre.class or Book.class");

                    if (byId == null)
                        throw new EmptyResultDataAccessException(0);
                    return byId;
                });
    }
    private Author findAuthorById(int id) {
        ObjectHolder foundAuthor = new ObjectHolder();
        authors.forEach(action -> {
            if (action.getId() == id) {
                foundAuthor.value = (org.omg.CORBA.Object) action;
            }
        });
        if(foundAuthor.value == null)
            throw new EmptyResultDataAccessException("EmptyResultDataAccessException", 0);
        return (Author) foundAuthor.value;
    }
    private Genre findGenreById(int id) {
        ObjectHolder foundGenre = new ObjectHolder();
        genres.forEach(action -> {
            if (action.getId() == id) {
                foundGenre.value = (org.omg.CORBA.Object) action;
            }
        });
        if(foundGenre.value == null)
            throw new EmptyResultDataAccessException("EmptyResultDataAccessException", 0);
        return (Genre) foundGenre.value;
    }
    private Book findBookById(int id) {
        ObjectHolder foundBook = new ObjectHolder();
        genres.forEach(action -> {
            if (action.getId() == id) {
                foundBook.value = (org.omg.CORBA.Object) action;
            }
        });
        if(foundBook.value == null)
            throw new EmptyResultDataAccessException("EmptyResultDataAccessException", 0);
        return (Book) foundBook.value;
    }

    private void initLibraryDaoMockForFind() {
        when(dao.find(any(Storable.class))).
                thenAnswer(answer -> {
                    Object object = (answer.getArguments())[0];
                    if (object instanceof Author) {
                        return findAuthor((Author) object);
                    } else if (object instanceof Genre) {
                        return findGenre((Genre) object);
                    } else if (object instanceof Book) {
                        return findBook((Book) object);
                    } else
                        throw new IllegalArgumentException("Must be object instanceof Author or Genre or Book");
                });
    }
    private List<Author> findAuthor(Author criterionAuthor) {
        if(criterionAuthor.getFirstname() == null && criterionAuthor.getLastname() == null)
            throw new IllegalArgumentException();
        List<Author> list = new ArrayList<>();
        authors.forEach(action -> {
            boolean res = true;
            if(criterionAuthor.getFirstname() != null) {
                if(!action.getFirstname().equals(criterionAuthor.getFirstname()))
                    res = false;
            }
            if(criterionAuthor.getLastname() != null) {
                if(!action.getLastname().equals(criterionAuthor.getLastname()))
                    res = false;
            }
            if(res)
                list.add(action);
        });
        return list;
    }
    private List<Genre> findGenre(Genre criterionGenre) {
        if(criterionGenre.getTitle() == null)
            throw new IllegalArgumentException();
        List<Genre> list = new ArrayList<>();
        genres.forEach(action -> {
            boolean res = true;
            if(!action.getTitle().equals(criterionGenre.getTitle()))
                res = false;
            if(res)
                list.add(action);
        });
        return list;
    }
    private List<Book> findBook(Book criterionBook) {
        if(criterionBook.getTitle() == null)
            throw new IllegalArgumentException();
        List<Book> list = new ArrayList<>();
        books.forEach(action -> {
            boolean res = true;
            if(!action.getTitle().equals(criterionBook.getTitle()))
                res = false;
            if(criterionBook.getAuthor().getFirstname() != null) {
                if(!action.getAuthor().getFirstname().equals(criterionBook.getAuthor().getFirstname()))
                    res = false;
            }
            if(criterionBook.getAuthor().getLastname() != null) {
                if(!action.getAuthor().getLastname().equals(criterionBook.getAuthor().getLastname()))
                    res = false;
            }
            if(criterionBook.getGenre().getTitle() != null) {
                if(!action.getGenre().getTitle().equals(criterionBook.getGenre().getTitle()))
                    res = false;
            }
            if(res)
                list.add(action);
        });
        return list;
    }

    private void initLibraryDaoMockForUpdate() {
        when(dao.update(any(Integer.class), any(Storable .class))).
                thenAnswer(answer -> {
                    int id = (int)(answer.getArguments())[0];
                    Object object = (answer.getArguments())[1];
                    if(object instanceof Author) {
                        return updateAuthor(id, (Author)object);
                    }
                    else if(object instanceof Genre) {
                        return updateGenre(id, (Genre)object);
                    }
                    else if(object instanceof Book) {
                        return updateBook(id, (Book)object);
                    }
                    else
                        throw new IllegalArgumentException("Must be object instanceof Author or Genre or Book");
                });

    }
    private int updateAuthor(int id, Author newAuthor) {
        Author authorById = findAuthorById(id);
        if(authorById != null) {
            authors.remove(authorById);
            newAuthor.setId(++authorCount);
            authors.add(newAuthor);
            return 1;
        }
        return 0;
    }
    private int updateGenre(int id, Genre newGenre) {
        Genre genreById = findGenreById(id);
        if(genreById != null) {
            authors.remove(genreById);
            newGenre.setId(++genreCount);
            genres.add(newGenre);
            return 1;
        }
        return 0;
    }
    private int updateBook(int id, Book newBook) {
        Book bookById = findBookById(id);
        if(bookById != null) {
            books.remove(bookById);
            newBook.setId(++genreCount);
            books.add(newBook);
            return 1;
        }
        return 0;
    }

    private void initLibraryDaoMockForDelete() {
        when(dao.delete(any(Class.class), any(Integer.class))).
            thenAnswer(answer -> {
                Class cl = (Class)(answer.getArguments())[0];
                int id = (int)(answer.getArguments())[1];
                if(cl == Author.class) {
                    return deleteAuthor(id);
                }
                else if(cl == Genre.class) {
                    return deleteGenre(id);
                }
                else if(cl == Book.class) {
                    return deleteBook(id);
                }
                return 1;
            });
    }
    private int deleteAuthor(int id) {
        Author authorById = findAuthorById(id);
        if (authorById != null) {
            if(authors.remove(authorById)) {
                return 1;
            }
            return 0;
        }
        return 0;
    }
    private int deleteGenre(int id) {
        Genre genreById = findGenreById(id);
        if (genreById != null) {
            if(genres.remove(genreById)) {
                return 1;
            }
            return 0;
        }
        return 0;
    }
    private int deleteBook(int id) {
        Book bookById = findBookById(id);
        if (bookById != null) {
            if(books.remove(bookById)) {
                return 1;
            }
            return 0;
        }
        return 0;
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