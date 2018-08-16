package com.martin.service;

import com.martin.caching.Cache;
import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import com.martin.domain.Storable;
import org.checkerframework.checker.units.qual.A;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.martin.service.Helper.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorJpaServiceTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private BookService bookService;

    @After
    public void tearDown() throws Exception {
        bookService.deleteAll();
        authorService.deleteAll();
        genreService.deleteAll();
    }

    @Test
    public void checkCacheAfterAddAuthor() throws Exception {
//        Author aPetrov = authorService.add("Alex", "Petrov");
//        Author iPetrov =authorService.add("Ivan", "Petrov");
//        Author kPetrov =authorService.add("Kirill", "Petrov");
//
//        List<Author> all = authorService.getAll(0, 100);
//        for (Author a: all) {
//            System.out.println(a);
//        }
//        Map<Long, ? extends Storable> cache = Cache.getCache(Author.class);
//        Author aPetrovCache = (Author) cache.get(aPetrov.getId());
//        System.out.println("aPetrovCache = " + aPetrovCache);
//        Author iPetrovCache = (Author)cache.get(iPetrov.getId());
//        System.out.println("iPetrovCache = " + iPetrovCache);
//        Author kPetrovCache = (Author)cache.get(kPetrov.getId());
//        System.out.println("kPetrovCache = " + kPetrovCache);
//
//        assertEquals(aPetrov, aPetrovCache);
//        assertEquals(iPetrov, iPetrovCache);
//        assertEquals(kPetrov, kPetrovCache);
//
//        authorService.deleteAll();
    }

    @Test
    public void checkCacheBeforeGetAuthorById() throws Exception {
//        Author aPetrov = authorService.add("Alex", "Petrov");
//        Author iPetrov =authorService.add("Ivan", "Petrov");
//        Author kPetrov =authorService.add("Kirill", "Petrov");
//
//        Author byId = authorService.findById(aPetrov.getId());
//        assertTrue(aPetrov.equals(byId));
    }

    @Test
    public void getExceptionAfterDoubleInsertOneAuthor() throws Exception {
        String firstname = "Alex";
        String lastname = "Petrov";
        Author author = new Author(firstname, lastname);
        boolean ok = false;
        authorService.add(firstname, lastname);
        try {
            authorService.add(firstname, lastname);
        }
        catch (Exception exception){
            if(exception.getMessage().equals(String.format(DUPLICATE_ERROR_STRING, author))) {
                ok = true;
            }
        }
        assertTrue(ok);
    }

    @Test
    public void getExceptionAfterInsertAuthorWithLongName() {
        String firstname = "1234567890123456789012345678901234567890";
        String lastname = "qwwq";
        boolean ok = false;
        Author author = new Author(firstname, lastname);
        try {
            authorService.add(firstname, lastname);
        }
        catch (Exception exception){
            if(exception.getMessage().equals(String.format(FORMAT_ERROR_STRING, author))) {
                ok = true;
            }
        }
        assertTrue(ok);
    }

    @Test
    public void checkGetCount() throws Exception {
        long before = authorService.getCount();
        authorService.add("Alex", "Petrov");
        authorService.add("Ivan", "Petrov");
        authorService.add("Kirill", "Petrov");
        long after = authorService.getCount();
        assertEquals(after-before, 3);
    }


    @Test
    public void getExceptionAfterFindByIdWithNotRealId() throws Exception {
        authorService.add("Alex", "Petrov");
        authorService.add("Ivan", "Petrov");
        authorService.add("Kirill", "Petrov");
        int id = 1221;
        boolean ok = false;
        try {
            authorService.findById(id);
        }
        catch (Exception exception) {
            if(exception.getMessage().equals(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Author.class.getSimpleName(), id))) {
                ok = true;
            }
        }
        assertTrue(ok);
    }

    @Test
    public void checkFind() throws Exception {
        Author aPetrov = authorService.add("Alex", "Petrov");
        Author iDzuba = authorService.add("Ivan", "Dzuba");
        Author aSidirov = authorService.add("Alex", "Sidorov");
        Author lSidirov = authorService.add("Leo", "Sidorov");
        List<Author> authors;
        authors = authorService.find("Alex", null);
        System.out.println("Find Alex null");
        for (Author author:authors) {
            System.out.println(author);
        }
        assertTrue(authors.contains(aPetrov) && authors.contains(aSidirov) && !authors.contains(iDzuba) && !authors.contains(lSidirov));

        authors = authorService.find(null, "Sidorov");
        System.out.println("Find null, Sidorov");
        for (Author author:authors) {
            System.out.println(author);
        }
        assertTrue(authors.contains(aSidirov) && authors.contains(lSidirov) && !authors.contains(iDzuba) && !authors.contains(aPetrov));

        authors = authorService.find("Alex", "Petrov");
        System.out.println("Find Alex, Sidorov");
        for (Author author:authors) {
            System.out.println(author);
        }

        assertTrue(authors.contains(aPetrov) && authors.contains(aSidirov));
    }


    @Test
    public void getExceptionAfterDeleteAuthorWithBook() throws Exception {
        Author aPetrov = authorService.add("Alex", "Petrov");
        Genre horror = genreService.add("Horror");

        bookService.add("title", aPetrov.getId(), horror.getId());

        boolean ok = false;
        try {
            authorService.delete(aPetrov.getId());
        }
        catch (Exception exception) {
            if(exception.getMessage().equals(String.format(ASSOCIATED_ERROR_STRING, Author.class.getSimpleName(), Book.class.getSimpleName()))) {
                ok = true;
            }
        }
        assertTrue(ok);
    }
}
