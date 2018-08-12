package com.martin.service;

import com.martin.domain.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.martin.service.Helper.DUPLICATE_ERROR_STRING;
import static com.martin.service.Helper.EMPTY_RESULT_BY_ID_ERROR_STRING;
import static com.martin.service.Helper.FORMAT_ERROR_STRING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorJpaServiceTest {

    @Autowired
    private AuthorService authorService;

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
        authorService.deleteAll();
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
        authorService.deleteAll();
    }

    @Test
    public void checkGetCount() throws Exception {
        long before = authorService.getCount();
        authorService.add("Alex", "Petrov");
        authorService.add("Ivan", "Petrov");
        authorService.add("Kirill", "Petrov");
        long after = authorService.getCount();
        assertEquals(after-before, 3);
        authorService.deleteAll();
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
        authorService.deleteAll();
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
        authorService.deleteAll();
    }


    @Test
    public void deleteWithBook() throws Exception {
        Author aPetrov = authorService.add("Alex", "Petrov");
        Author iDzuba = authorService.add("Ivan", "Dzuba");
        authorService.deleteAll();
    }
}
